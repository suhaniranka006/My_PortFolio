package com.example.my_portfolio.ui.screens

import android.net.Uri
import android.widget.VideoView
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.LaptopMac
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.my_portfolio.R
import kotlin.math.absoluteValue

// --- DATA MODELS ---

sealed class MediaItem {
    data class Image(val drawableId: Int) : MediaItem()
    data class Video(val thumbnailId: Int, val rawId: Int) : MediaItem()
}

data class Project(
    val title: String,
    val description: String,
    val technologies: List<String>,
    val backgroundIcon: ImageVector,
    val media: List<MediaItem>
)

// --- YOUR PROJECT DATA ---

val projectList = listOf(
    Project(
        title = "JainConnect",
        description = "A community-focused application to connect members of the Jain community, featuring event updates, a business directory, and matrimonial services. The backend is powered by MongoDB, communicating via a Retrofit-based API.",
        technologies = listOf("MongoDB", "Kotlin", "Retrofit", "Android", "MVVM"),
        backgroundIcon = Icons.Outlined.Share,
        media = listOf(
            MediaItem.Image(R.drawable.profile_pic), // Replace with your screenshot
            MediaItem.Image(R.drawable.profile_pic)  // Replace with your screenshot
        )
    ),
    Project(
        title = "FoodiHelp",
        description = "A platform that connects restaurants with excess food to local NGOs. It uses Firebase for real-time data synchronization and notifications, all built on a robust MVVM architecture.",
        technologies = listOf("Firebase", "Kotlin", "MVVM", "Android", "Google Maps SDK"),
        backgroundIcon = Icons.Outlined.PhoneAndroid,
        media = listOf(
            MediaItem.Video(R.drawable.profile, R.raw.sample_video), // Replace thumbnail and video
            MediaItem.Image(R.drawable.profile_pic) // Replace with your screenshot
        )
    ),
    Project(
        title = "My Portfolio App",
        description = "A personal portfolio application built with modern practices to showcase my skills, projects, and experience in a clean, interactive, and fully native format.",
        technologies = listOf("Kotlin", "Jetpack Compose", "Android", "Material Design 3"),
        backgroundIcon = Icons.Outlined.LaptopMac,
        media = listOf(
            MediaItem.Image(R.drawable.profile_pic), // Replace with your screenshot
            MediaItem.Image(R.drawable.profile_pic)  // Replace with your screenshot
        )
    )
)

// --- MAIN SCREEN COMPOSABLE ---

@Composable
fun ProjectsScreen() {
    val pagerState = rememberPagerState(pageCount = { projectList.size })
    var selectedMediaItem by remember { mutableStateOf<MediaItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Featured Projects",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        ) { page ->
            ProjectPagerItem(
                project = projectList[page],
                pagerState = pagerState,
                page = page,
                onMediaClick = { media ->
                    selectedMediaItem = media
                }
            )
        }

        PagerIndicator(
            pageCount = projectList.size,
            currentPage = pagerState.currentPage
        )
    }

    // --- Full-Screen Media Viewer Dialog ---
    if (selectedMediaItem != null) {
        FullScreenMediaViewer(
            mediaItem = selectedMediaItem!!,
            onDismiss = { selectedMediaItem = null }
        )
    }
}

// --- UI COMPONENTS ---

@Composable
fun ProjectPagerItem(
    project: Project,
    pagerState: PagerState,
    page: Int,
    onMediaClick: (MediaItem) -> Unit
) {
    val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
    val imageSize = 180.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = project.backgroundIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            modifier = Modifier
                .size(imageSize)
                .graphicsLayer {
                    translationX = size.width * (pageOffset * 0.5f)
                    alpha = 1f - pageOffset.absoluteValue
                }
        )

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
                .graphicsLayer {
                    alpha = 1f - (pageOffset.absoluteValue * 0.5f)
                    scaleY = 1f - (pageOffset.absoluteValue * 0.1f)
                },
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = project.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = project.description, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(project.technologies) { techName ->
                        TechnologyChip(name = techName)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                MediaShowcase(mediaItems = project.media, onMediaClick = onMediaClick)
            }
        }
    }
}

@Composable
fun MediaShowcase(mediaItems: List<MediaItem>, onMediaClick: (MediaItem) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Screenshots & Video", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(mediaItems) { media ->
                when (media) {
                    is MediaItem.Image -> {
                        Image(
                            painter = painterResource(id = media.drawableId),
                            contentDescription = "Project screenshot",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(120.dp)
                                .aspectRatio(9f / 16f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onMediaClick(media) }
                        )
                    }
                    is MediaItem.Video -> {
                        VideoThumbnail(thumbnailId = media.thumbnailId, onClick = { onMediaClick(media) })
                    }
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(thumbnailId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(120.dp)
            .aspectRatio(9f / 16f)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = thumbnailId), contentDescription = "Video Thumbnail", contentScale = ContentScale.Crop)
        Box(modifier = Modifier.matchParentSize().background(Color.Black.copy(alpha = 0.3f)))
        Icon(imageVector = Icons.Default.PlayCircle, contentDescription = "Play Video", tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(48.dp))
    }
}

@Composable
fun FullScreenMediaViewer(mediaItem: MediaItem, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center
        ) {
            when (mediaItem) {
                is MediaItem.Image -> {
                    Image(
                        painter = painterResource(id = mediaItem.drawableId),
                        contentDescription = "Full screen image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
                is MediaItem.Video -> {
                    FullScreenVideoPlayer(rawId = mediaItem.rawId)
                }
            }
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }
        }
    }
}

@Composable
fun FullScreenVideoPlayer(rawId: Int) {
    val context = LocalContext.current
    val videoUri = Uri.parse("android.resource://${context.packageName}/$rawId")

    // 1. Create and remember the VideoView instance so it's not recreated on recomposition
    val videoView = remember {
        VideoView(context).apply {
            setVideoURI(videoUri)
            val mediaController = android.widget.MediaController(context)
            mediaController.setAnchorView(this)
            setMediaController(mediaController)
        }
    }

    // 2. Use AndroidView to embed the remembered VideoView instance in the Compose hierarchy
    AndroidView(factory = { videoView }, modifier = Modifier.fillMaxWidth())

    // 3. Use DisposableEffect to manage the video playback lifecycle
    DisposableEffect(Unit) {
        videoView.start() // Start playing when the view appears
        onDispose {
            videoView.stopPlayback() // Stop and release resources when the view disappears
        }
    }
}


@Composable
fun PagerIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 32.dp, top = 16.dp)
    ) {
        repeat(pageCount) { iteration ->
            val isSelected = currentPage == iteration
            val color by animateColorAsState(targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), animationSpec = tween(300), label = "")
            val size by animateDpAsState(targetValue = if (isSelected) 12.dp else 8.dp, animationSpec = tween(300), label = "")
            Box(modifier = Modifier.size(size).clip(CircleShape).background(color))
        }
    }
}

@Composable
fun TechnologyChip(name: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = name, color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

