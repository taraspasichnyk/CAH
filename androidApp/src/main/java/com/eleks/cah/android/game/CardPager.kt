package com.eleks.cah.android.game.round

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.SnapperFlingBehavior
import dev.chrisbanes.snapper.SnapperFlingBehaviorDefaults
import dev.chrisbanes.snapper.SnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter

/**
 *
 *
 *
 *
 *
 * Forked horizontal pager from google accompanist
 * library with addition of elevation for cards
 *
 *
 *
 *
 *
 */
/**
 * A horizontally scrolling layout that allows users to flip between items to the left and right.
 *
 * @sample com.google.accompanist.sample.pager.HorizontalPagerSample
 *
 * @param count the number of pages.
 * @param modifier the modifier to apply to this layout.
 * @param state the state object to be used to control or observe the pager's state.
 * @param reverseLayout reverse the direction of scrolling and layout, when `true` items will be
 * composed from the end to the start and [PagerState.currentPage] == 0 will mean
 * the first item is located at the end.
 * @param itemSpacing horizontal spacing to add between items.
 * @param flingBehavior logic describing fling behavior.
 * @param key the scroll position will be maintained based on the key, which means if you
 * add/remove items before the current visible item the item with the given key will be kept as the
 * first visible one.
 * @param userScrollEnabled whether the scrolling via the user gestures or accessibility actions
 * is allowed. You can still scroll programmatically using the state even when it is disabled.
 * @param content a block which describes the content. Inside this block you can reference
 * [PagerScope.currentPage] and other properties in [PagerScope].
 */
@OptIn(ExperimentalSnapperApi::class)
@ExperimentalPagerApi
@Composable
fun HorizontalPager(
    count: Int,
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(),
    reverseLayout: Boolean = false,
    itemSpacing: Dp = 0.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    flingBehavior: FlingBehavior = PagerDefaults.flingBehavior(
        state = state,
        endContentPadding = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
    ),
    key: ((page: Int) -> Any)? = null,
    userScrollEnabled: Boolean = true,
    elevationType: PageElevation,
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    Pager(
        count = count,
        state = state,
        modifier = modifier,
        isVertical = false,
        reverseLayout = reverseLayout,
        itemSpacing = itemSpacing,
        verticalAlignment = verticalAlignment,
        flingBehavior = flingBehavior,
        key = key,
        contentPadding = contentPadding,
        userScrollEnabled = userScrollEnabled,
        elevationType = elevationType,
        content = content
    )
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalPagerApi
@Composable
internal fun Pager(
    count: Int,
    modifier: Modifier,
    state: PagerState,
    reverseLayout: Boolean,
    itemSpacing: Dp,
    isVertical: Boolean,
    flingBehavior: FlingBehavior,
    key: ((page: Int) -> Any)?,
    contentPadding: PaddingValues,
    userScrollEnabled: Boolean,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    elevationType: PageElevation,
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    require(count >= 0) { "pageCount must be >= 0" }

    // Provide our PagerState with access to the SnappingFlingBehavior animation target
    // TODO: can this be done in a better way?
    state.flingAnimationTarget = {
        @OptIn(ExperimentalSnapperApi::class)
        (flingBehavior as? SnapperFlingBehavior)?.animationTarget
    }

    LaunchedEffect(count) {
        state.currentPage = minOf(count - 1, state.currentPage).coerceAtLeast(0)
    }

    // Once a fling (scroll) has finished, notify the state
    LaunchedEffect(state) {
        // When a 'scroll' has finished, notify the state
        snapshotFlow { state.isScrollInProgress }
            .filter { !it }
            // initially isScrollInProgress is false as well and we want to start receiving
            // the events only after the real scroll happens.
            .drop(1)
            .collect { state.onScrollFinished() }
    }
    LaunchedEffect(state) {
        snapshotFlow { state.mostVisiblePageLayoutInfo?.index }
            .distinctUntilChanged()
            .collect { state.updateCurrentPageBasedOnLazyListState() }
    }
    val density = LocalDensity.current
    LaunchedEffect(density, state, itemSpacing) {
        with(density) { state.itemSpacing = itemSpacing.roundToPx() }
    }

    val pagerScope = remember(state) { PagerScopeImpl(state) }

    // We only consume nested flings in the main-axis, allowing cross-axis flings to propagate
    // as normal
    val consumeFlingNestedScrollConnection = remember(isVertical) {
        ConsumeFlingNestedScrollConnection(
            consumeHorizontal = !isVertical,
            consumeVertical = isVertical,
            pagerState = state,
        )
    }

    if (isVertical) {
        LazyColumn(
            state = state.lazyListState,
            verticalArrangement = Arrangement.spacedBy(itemSpacing, verticalAlignment),
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            reverseLayout = reverseLayout,
            contentPadding = contentPadding,
            userScrollEnabled = userScrollEnabled,
            modifier = modifier,
        ) {
            items(
                count = count,
                key = key,
            ) { page ->
                val zIndex = when (elevationType) {
                    PageElevation.STACK -> 1f
                    PageElevation.REVERSE_STACK -> page.toFloat()
                    PageElevation.FOCUSED_ABOVE -> if (page == state.currentPage) {
                        state.pageCount.toFloat()
                    } else {
                        -abs(state.currentPage - page).toFloat()
                    }
                }

                Box(
                    Modifier
                        // We don't any nested flings to continue in the pager, so we add a
                        // connection which consumes them.
                        // See: https://github.com/google/accompanist/issues/347
                        .nestedScroll(connection = consumeFlingNestedScrollConnection)
                        .zIndex(zIndex)
                        .animateItemPlacement()
                        // Constraint the content height to be <= than the height of the pager.
                        .fillParentMaxHeight()
                        .wrapContentSize()
                ) {
                    pagerScope.content(page)
                }
            }
        }
    } else {
        LazyRow(
            state = state.lazyListState,
            verticalAlignment = verticalAlignment,
            horizontalArrangement = Arrangement.spacedBy(itemSpacing, horizontalAlignment),
            flingBehavior = flingBehavior,
            reverseLayout = reverseLayout,
            contentPadding = contentPadding,
            userScrollEnabled = userScrollEnabled,
            modifier = modifier,
        ) {
            items(
                count = count,
                key = key,
            ) { page ->
                val zIndex = when (elevationType) {
                    PageElevation.STACK -> 1f
                    PageElevation.REVERSE_STACK -> page.toFloat()
                    PageElevation.FOCUSED_ABOVE -> if (page == state.currentPage) {
                        state.pageCount.toFloat()
                    } else {
                        -abs(state.currentPage - page).toFloat()
                    }
                }

                Box(
                    Modifier
                        // We don't any nested flings to continue in the pager, so we add a
                        // connection which consumes them.
                        // See: https://github.com/google/accompanist/issues/347
                        .nestedScroll(connection = consumeFlingNestedScrollConnection)
                        .zIndex(zIndex)
                        .animateItemPlacement()
                        // Constraint the content width to be <= than the width of the pager.
                        .fillParentMaxWidth()
                        .wrapContentSize()
                ) {
                    pagerScope.content(page)
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Stable
class PagerState(
    @IntRange(from = 0) currentPage: Int = 0,
) : ScrollableState {
    // Should this be public?
    internal val lazyListState = LazyListState(firstVisibleItemIndex = currentPage)

    private var _currentPage by mutableStateOf(currentPage)

    // finds the page which has larger visible area within the viewport not including paddings
    internal val mostVisiblePageLayoutInfo: LazyListItemInfo?
        get() {
            val layoutInfo = lazyListState.layoutInfo
            return layoutInfo.visibleItemsInfo.maxByOrNull {
                val start = maxOf(it.offset, 0)
                val end = minOf(
                    it.offset + it.size,
                    layoutInfo.viewportEndOffset - layoutInfo.afterContentPadding
                )
                end - start
            }
        }

    internal var itemSpacing by mutableStateOf(0)

    private val currentPageLayoutInfo: LazyListItemInfo?
        get() = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull {
            it.index == currentPage
        }

    /**
     * [InteractionSource] that will be used to dispatch drag events when this
     * list is being dragged. If you want to know whether the fling (or animated scroll) is in
     * progress, use [isScrollInProgress].
     */
    val interactionSource: InteractionSource
        get() = lazyListState.interactionSource

    /**
     * The number of pages to display.
     */
    @get:IntRange(from = 0)
    val pageCount: Int by derivedStateOf {
        lazyListState.layoutInfo.totalItemsCount
    }

    /**
     * The index of the currently selected page. This may not be the page which is
     * currently displayed on screen.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    @get:IntRange(from = 0)
    var currentPage: Int
        get() = _currentPage
        internal set(value) {
            if (value != _currentPage) {
                _currentPage = value
            }
        }

    /**
     * The current offset from the start of [currentPage], as a ratio of the page width.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    val currentPageOffset: Float by derivedStateOf {
        currentPageLayoutInfo?.let {
            (-it.offset / (it.size + itemSpacing).toFloat()).coerceIn(-0.5f, 0.5f)
        } ?: 0f
    }

    /**
     * The target page for any on-going animations.
     */
    private var animationTargetPage: Int? by mutableStateOf(null)

    internal var flingAnimationTarget: (() -> Int?)? by mutableStateOf(null)

    /**
     * The target page for any on-going animations or scrolls by the user.
     * Returns the current page if a scroll or animation is not currently in progress.
     */
    @Deprecated(
        "targetPage is deprecated in favor of currentPage as currentPage property is" +
                "now being updated right after we over scrolled the half of the previous current page." +
                "If you still think that you need targetPage, not currentPage please file a bug as " +
                "we are planning to remove this property in future.",
        ReplaceWith("currentPage")
    )
    val targetPage: Int
        get() = animationTargetPage
            ?: flingAnimationTarget?.invoke()
            ?: when {
                // If a scroll isn't in progress, return the current page
                !isScrollInProgress -> currentPage
                // If the offset is 0f (or very close), return the current page
                currentPageOffset.absoluteValue < 0.001f -> currentPage
                // If we're offset towards the start, guess the previous page
                currentPageOffset < 0f -> (currentPage - 1).coerceAtLeast(0)
                // If we're offset towards the end, guess the next page
                else -> (currentPage + 1).coerceAtMost(pageCount - 1)
            }

    @Deprecated(
        "Replaced with animateScrollToPage(page, pageOffset)",
        ReplaceWith("animateScrollToPage(page = page, pageOffset = pageOffset)")
    )
    @Suppress("UNUSED_PARAMETER")
    suspend fun animateScrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
        animationSpec: AnimationSpec<Float> = spring(),
        initialVelocity: Float = 0f,
        skipPages: Boolean = true,
    ) {
        animateScrollToPage(page = page, pageOffset = pageOffset)
    }

    /**
     * Animate (smooth scroll) to the given page to the middle of the viewport.
     *
     * Cancels the currently running scroll, if any, and suspends until the cancellation is
     * complete.
     *
     * @param page the page to animate to. Must be >= 0.
     * @param pageOffset the percentage of the page size to offset, from the start of [page].
     * Must be in the range -1f..1f.
     */
    suspend fun animateScrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = -1.0, to = 1.0) pageOffset: Float = 0f,
    ) {
        try {
            animationTargetPage = page

            // pre-jump to nearby item for long jumps as an optimization
            // the same trick is done in ViewPager2
            val oldPage = lazyListState.firstVisibleItemIndex
            if (abs(page - oldPage) > 3) {
                lazyListState.scrollToItem(if (page > oldPage) page - 3 else page + 3)
            }

            if (pageOffset.absoluteValue <= 0.005f) {
                // If the offset is (close to) zero, just call animateScrollToItem and we're done
                lazyListState.animateScrollToItem(index = page)
            } else {
                // Else we need to figure out what the offset is in pixels...
                lazyListState.scroll { } // this will await for the first layout.
                val layoutInfo = lazyListState.layoutInfo
                var target = layoutInfo.visibleItemsInfo
                    .firstOrNull { it.index == page }

                if (target != null) {
                    // If we have access to the target page layout, we can calculate the pixel
                    // offset from the size
                    lazyListState.animateScrollToItem(
                        index = page,
                        scrollOffset = ((target.size + itemSpacing) * pageOffset).roundToInt()
                    )
                } else if (layoutInfo.visibleItemsInfo.isNotEmpty()) {
                    // If we don't, we use the current page size as a guide
                    val currentSize = layoutInfo.visibleItemsInfo.first().size + itemSpacing
                    lazyListState.animateScrollToItem(
                        index = page,
                        scrollOffset = (currentSize * pageOffset).roundToInt()
                    )

                    // The target should be visible now
                    target = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == page }

                    if (target != null && target.size + itemSpacing != currentSize) {
                        // If the size we used for calculating the offset differs from the actual
                        // target page size, we need to scroll again. This doesn't look great,
                        // but there's not much else we can do.
                        lazyListState.animateScrollToItem(
                            index = page,
                            scrollOffset = ((target.size + itemSpacing) * pageOffset).roundToInt()
                        )
                    }
                }
            }
        } finally {
            // We need to manually call this, as the `animateScrollToItem` call above will happen
            // in 1 frame, which is usually too fast for the LaunchedEffect in Pager to detect
            // the change. This is especially true when running unit tests.
            onScrollFinished()
        }
    }

    /**
     * Instantly brings the item at [page] to the middle of the viewport.
     *
     * Cancels the currently running scroll, if any, and suspends until the cancellation is
     * complete.
     *
     * @param page the page to snap to. Must be >= 0.
     * @param pageOffset the percentage of the page size to offset, from the start of [page].
     * Must be in the range -1f..1f.
     */
    suspend fun scrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = -1.0, to = 1.0) pageOffset: Float = 0f,
    ) {
        try {
            animationTargetPage = page

            // First scroll to the given page. It will now be laid out at offset 0
            lazyListState.scrollToItem(index = page)
            updateCurrentPageBasedOnLazyListState()

            // If we have a start spacing, we need to offset (scroll) by that too
            if (pageOffset.absoluteValue > 0.0001f) {
                currentPageLayoutInfo?.let {
                    scroll {
                        scrollBy((it.size + itemSpacing) * pageOffset)
                    }
                }
            }
        } finally {
            // We need to manually call this, as the `scroll` call above will happen in 1 frame,
            // which is usually too fast for the LaunchedEffect in Pager to detect the change.
            // This is especially true when running unit tests.
            onScrollFinished()
        }
    }

    internal fun updateCurrentPageBasedOnLazyListState() {
        // Then update the current page to our layout page
        mostVisiblePageLayoutInfo?.let {
            currentPage = it.index
        }
    }

    internal fun onScrollFinished() {
        // Clear the animation target page
        animationTargetPage = null
    }

    override suspend fun scroll(
        scrollPriority: MutatePriority,
        block: suspend ScrollScope.() -> Unit
    ) = lazyListState.scroll(scrollPriority, block)

    override fun dispatchRawDelta(delta: Float): Float {
        return lazyListState.dispatchRawDelta(delta)
    }

    override val isScrollInProgress: Boolean
        get() = lazyListState.isScrollInProgress

    override fun toString(): String = "PagerState(" +
            "pageCount=$pageCount, " +
            "currentPage=$currentPage, " +
            "currentPageOffset=$currentPageOffset" +
            ")"

    private fun requireCurrentPage(value: Int, name: String) {
        require(value >= 0) { "$name[$value] must be >= 0" }
    }

    private fun requireCurrentPageOffset(value: Float, name: String) {
        require(value in -1f..1f) { "$name must be >= -1 and <= 1" }
    }

    companion object {
        val Saver: Saver<PagerState, *> = listSaver(
            save = {
                listOf<Any>(
                    it.currentPage,
                )
            },
            restore = {
                PagerState(
                    currentPage = it[0] as Int,
                )
            }
        )
    }
}

@ExperimentalPagerApi
private class PagerScopeImpl(
    private val state: PagerState,
) : PagerScope {
    override val currentPage: Int get() = state.currentPage
    override val currentPageOffset: Float get() = state.currentPageOffset
}

@OptIn(ExperimentalPagerApi::class)
private class ConsumeFlingNestedScrollConnection(
    private val consumeHorizontal: Boolean,
    private val consumeVertical: Boolean,
    private val pagerState: PagerState,
) : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when (source) {
        // We can consume all resting fling scrolls so that they don't propagate up to the
        // Pager
        NestedScrollSource.Fling -> available.consume(consumeHorizontal, consumeVertical)
        else -> Offset.Zero
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        return if (pagerState.currentPageOffset != 0f) {
            // The Pager is already scrolling. This means that a nested scroll child was
            // scrolled to end, and the Pager can use this fling
            Velocity.Zero
        } else {
            // A nested scroll child is still scrolling. We can consume all post fling
            // velocity on the main-axis so that it doesn't propagate up to the Pager
            available.consume(consumeHorizontal, consumeVertical)
        }
    }
}

private fun Offset.consume(
    consumeHorizontal: Boolean,
    consumeVertical: Boolean,
): Offset = Offset(
    x = if (consumeHorizontal) this.x else 0f,
    y = if (consumeVertical) this.y else 0f,
)

private fun Velocity.consume(
    consumeHorizontal: Boolean,
    consumeVertical: Boolean,
): Velocity = Velocity(
    x = if (consumeHorizontal) this.x else 0f,
    y = if (consumeVertical) this.y else 0f,
)

@ExperimentalPagerApi
@Composable
fun rememberPagerState(
    @IntRange(from = 0) initialPage: Int = 0,
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        currentPage = initialPage,
    )
}

@ExperimentalPagerApi
object PagerDefaults {
    /**
     * The default implementation for the `maximumFlingDistance` parameter of
     * [flingBehavior] which limits the fling distance to a single page.
     */
    @ExperimentalSnapperApi
    @Suppress("MemberVisibilityCanBePrivate")
    @Deprecated("MaximumFlingDistance has been deprecated in Snapper")
    val singlePageFlingDistance: (SnapperLayoutInfo) -> Float = { layoutInfo ->
        // We can scroll up to the scrollable size of the lazy layout
        layoutInfo.endScrollOffset - layoutInfo.startScrollOffset.toFloat()
    }

    /**
     * The default implementation for the `snapIndex` parameter of
     * [flingBehavior] which limits the fling distance to a single page.
     */
    @ExperimentalSnapperApi
    val singlePageSnapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int =
        { layoutInfo, startIndex, targetIndex ->
            targetIndex
                .coerceIn(startIndex - 1, startIndex + 1)
                .coerceIn(0, layoutInfo.totalItemsCount - 1)
        }

    /**
     * Remember the default [FlingBehavior] that represents the scroll curve.
     *
     * Please remember to provide the correct [endContentPadding] if supplying your own
     * [FlingBehavior] to [VerticalPager] or [HorizontalPager]. See those functions for how they
     * calculate the value.
     *
     * @param state The [PagerState] to update.
     * @param decayAnimationSpec The decay animation spec to use for decayed flings.
     * @param snapAnimationSpec The animation spec to use when snapping.
     * @param maximumFlingDistance Block which returns the maximum fling distance in pixels.
     * @param endContentPadding The amount of content padding on the end edge of the lazy list
     * in pixels (end/bottom depending on the scrolling direction).
     */
    @Composable
    @ExperimentalSnapperApi
    @Deprecated("MaximumFlingDistance has been deprecated in Snapper, replaced with snapIndex")
    @Suppress("DEPRECATION")
    fun flingBehavior(
        state: PagerState,
        decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
        snapAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        maximumFlingDistance: (SnapperLayoutInfo) -> Float = singlePageFlingDistance,
        endContentPadding: Dp = 0.dp,
    ): FlingBehavior = rememberSnapperFlingBehavior(
        lazyListState = state.lazyListState,
        snapOffsetForItem = SnapOffsets.Start, // pages are full width, so we use the simplest
        decayAnimationSpec = decayAnimationSpec,
        springAnimationSpec = snapAnimationSpec,
        maximumFlingDistance = maximumFlingDistance,
        endContentPadding = endContentPadding,
    )

    /**
     * Remember the default [FlingBehavior] that represents the scroll curve.
     *
     * Please remember to provide the correct [endContentPadding] if supplying your own
     * [FlingBehavior] to [VerticalPager] or [HorizontalPager]. See those functions for how they
     * calculate the value.
     *
     * @param state The [PagerState] to update.
     * @param decayAnimationSpec The decay animation spec to use for decayed flings.
     * @param snapAnimationSpec The animation spec to use when snapping.
     * @param endContentPadding The amount of content padding on the end edge of the lazy list
     * in pixels (end/bottom depending on the scrolling direction).
     * @param snapIndex Block which returns the index to snap to. The block is provided with the
     * [SnapperLayoutInfo], the index where the fling started, and the index which Snapper has
     * determined is the correct target index. Callers can override this value to any valid index
     * for the layout. Some common use cases include limiting the fling distance, and rounding up/down
     * to achieve snapping to groups of items.
     */
    @Composable
    @ExperimentalSnapperApi
    fun flingBehavior(
        state: PagerState,
        decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
        snapAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        endContentPadding: Dp = 0.dp,
        snapIndex: (SnapperLayoutInfo, startIndex: Int, targetIndex: Int) -> Int,
    ): FlingBehavior = rememberSnapperFlingBehavior(
        lazyListState = state.lazyListState,
        snapOffsetForItem = SnapOffsets.Start, // pages are full width, so we use the simplest
        decayAnimationSpec = decayAnimationSpec,
        springAnimationSpec = snapAnimationSpec,
        endContentPadding = endContentPadding,
        snapIndex = snapIndex,
    )

    /**
     * Remember the default [FlingBehavior] that represents the scroll curve.
     *
     * Please remember to provide the correct [endContentPadding] if supplying your own
     * [FlingBehavior] to [VerticalPager] or [HorizontalPager]. See those functions for how they
     * calculate the value.
     *
     * @param state The [PagerState] to update.
     * @param decayAnimationSpec The decay animation spec to use for decayed flings.
     * @param snapAnimationSpec The animation spec to use when snapping.
     * @param endContentPadding The amount of content padding on the end edge of the lazy list
     * in pixels (end/bottom depending on the scrolling direction).
     */
    @Composable
    @ExperimentalSnapperApi
    fun flingBehavior(
        state: PagerState,
        decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
        snapAnimationSpec: AnimationSpec<Float> = SnapperFlingBehaviorDefaults.SpringAnimationSpec,
        endContentPadding: Dp = 0.dp,
    ): FlingBehavior {
        // You might be wondering this is function exists rather than a default value for snapIndex
        // above. It was done to remove overload ambiguity with the maximumFlingDistance overload
        // below. When that function is removed, we also remove this function and move to a default
        // param value.
        return flingBehavior(
            state = state,
            decayAnimationSpec = decayAnimationSpec,
            snapAnimationSpec = snapAnimationSpec,
            endContentPadding = endContentPadding,
            snapIndex = singlePageSnapIndex,
        )
    }
}

@ExperimentalPagerApi
@Stable
interface PagerScope {
    /**
     * Returns the current selected page
     */
    val currentPage: Int

    /**
     * The current offset from the start of [currentPage], as a ratio of the page width.
     */
    val currentPageOffset: Float
}

@ExperimentalPagerApi
fun PagerScope.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffset
}

enum class PageElevation {
    STACK, REVERSE_STACK, FOCUSED_ABOVE
}