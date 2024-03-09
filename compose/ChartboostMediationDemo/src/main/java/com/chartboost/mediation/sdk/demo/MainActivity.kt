package com.chartboost.mediation.sdk.demo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.chartboost.heliumsdk.ad.HeliumBannerAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/**
 * This is a demo app for the Chartboost Mediation SDK. It demonstrates how to use the SDK with Jetpack Compose.
 * The app is written in Kotlin and uses the Compose UI toolkit.
 *
 * This Activity is the entry point of the app. It initializes the SDK and displays the UI.
 *
 * For ad loading and showing logic, reference the [AdController].
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Add the AdController as a lifecycle observer so that it can handle the lifecycle events
        // of the activity.
        lifecycle.addObserver(AdController)

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

/**
 * The main UI of the app. It contains buttons to load and show interstitial and rewarded ads.
 * Banner ads are loaded and shown automatically.
 *
 * Do any initialization and setup in here.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App() {
    // Get the context to initialize the SDK, and to load and show ads.
    val context = LocalContext.current

    // A list of log messages to display in the log console.
    val logState = remember { mutableStateListOf<String>() }

    // Get the app ID, app signature, and placement names from the strings.xml file.
    val appId = stringResource(R.string.app_id)
    val appSignature = stringResource(R.string.app_signature)
    val bannerPlacement = stringResource(R.string.banner_placement)
    val interstitialPlacement = stringResource(R.string.interstitial_placement)
    val rewardedAdPlacement = stringResource(R.string.rewarded_placement)

    // Whether the show buttons for interstitial and rewarded ads should be enabled. They are
    // disabled by default, and are enabled when the corresponding ad is loaded.
    val isInterstitialShowBtnEnabled = remember { mutableStateOf(false) }
    val isRewardedShowBtnEnabled = remember { mutableStateOf(false) }

    // Whether to use the fullscreen API for interstitial and rewarded ads. This is the new and recommended
    // way to load and show all fullscreen ads. It is enabled by default, but can be disabled by the user.
    var shouldUseFullscreenApi =
        remember {
            mutableStateOf(
                context.getSharedPreferences(
                    "com.chartboost.mediation.sdk.demo",
                    Context.MODE_PRIVATE,
                ).getBoolean("fullscreen_api", false),
            )
        }

    // Whether to use the fullscreen ad queue to load ads for each placement.
    var shouldUseFullscreenAdQueue =
        remember {
            mutableStateOf(
                context.getSharedPreferences(
                    "com.chartboost.mediation.sdk.demo",
                    Context.MODE_PRIVATE,
                ).getBoolean("fullscreen_ad_queue", true),
            )
        }

    // Whether the Chartboost Mediation SDK has been initialized. This is used to determine whether to
    // show the banner ad (since it is loaded automatically and should be done so after the SDK is initialized).
    var isSdkInitialized by remember { mutableStateOf(false) }

    Scaffold(
        content = {
            LazyColumn(
                Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                item {
                    // An "AdSection" is a row that contains a heading, a placement name, and buttons
                    // to load and show the ad.
                    AdSection(
                        heading = "Interstitial Ad",
                        placementName = interstitialPlacement,
                        loadAd = {
                            AdController.loadAd(
                                context,
                                AdController.AdType.INTERSTITIAL,
                                interstitialPlacement,
                                logState,
                                shouldUseFullscreenApi,
                                shouldUseFullscreenAdQueue,
                                isInterstitialShowBtnEnabled,
                            )
                        },
                        showAd = {
                            AdController.showAd(
                                context,
                                AdController.AdType.INTERSTITIAL,
                                interstitialPlacement,
                                logState,
                                shouldUseFullscreenApi,
                                shouldUseFullscreenAdQueue,
                                isInterstitialShowBtnEnabled,
                            )
                        },
                        enabled = isInterstitialShowBtnEnabled.value,
                    )
                }

                item {
                    AdSection(
                        heading = "Rewarded Video Ad",
                        placementName = rewardedAdPlacement,
                        loadAd = {
                            AdController.loadAd(
                                context,
                                AdController.AdType.REWARDED_VIDEO,
                                rewardedAdPlacement,
                                logState,
                                shouldUseFullscreenApi,
                                shouldUseFullscreenAdQueue,
                                isRewardedShowBtnEnabled,
                            )
                        },
                        showAd = {
                            AdController.showAd(
                                context,
                                AdController.AdType.REWARDED_VIDEO,
                                rewardedAdPlacement,
                                logState,
                                shouldUseFullscreenApi,
                                shouldUseFullscreenAdQueue,
                                isRewardedShowBtnEnabled,
                            )
                        },
                        enabled = isRewardedShowBtnEnabled.value,
                    )
                }

                item {
                    FullscreenApiToggle(
                        settingKey = "fullscreen_api",
                        context = context,
                        onSettingChange = { newValue ->
                            shouldUseFullscreenApi = mutableStateOf(newValue)
                            logState.add("Interstitial and rewarded ads will ${if (newValue) "use" else "not use"} the fullscreen API")
                        },
                    )
                }

                item {
                    FullscreenAdQueueToggle(
                        settingKey = "fullscreen_ad_queue",
                        context = context,
                        shouldUseFullscreenApi = shouldUseFullscreenApi.value,
                        onSettingChange = { newValue ->
                            shouldUseFullscreenAdQueue = mutableStateOf(newValue)
                            logState.add("Fullscreen Ad Queue will be ${if (newValue) "used" else "not used"}")
                        },
                    )
                }

                item {
                    // The log console displays the log messages.
                    LogConsole(logState)
                }

                item {
                    // The banner ad is loaded and shown automatically after the SDK is initialized.
                    if (isSdkInitialized) {
                        HeliumBannerAdCompose(bannerPlacement, logState)
                    }
                }
            }
        },
    )

    LaunchedEffect(key1 = "initialization") {
        logState.add("Initializing Chartboost Mediation SDK")

        // Initialize the Chartboost Mediation SDK on the main thread and keep track of the state.
        CoroutineScope(Main).launch {
            AdController.initialize(
                context,
                appId,
                appSignature,
                logState,
            )

            isSdkInitialized = true
        }
    }
}

/**
 * A row that contains a heading, a placement name, and buttons to load and show the ad.
 *
 * @param heading The heading of the section.
 * @param placementName The name of the placement to load and show.
 * @param loadAd A function to load the ad.
 * @param showAd A function to show the ad.
 * @param enabled Whether the show button should be enabled. It is disabled by default, and is enabled when the ad is loaded.
 */
@Composable
fun AdSection(
    heading: String,
    placementName: String,
    loadAd: () -> Unit,
    showAd: () -> Unit,
    enabled: Boolean = true,
) {
    Row {
        Text(text = heading)
        Text(
            text = placementName,
            modifier = Modifier.padding(start = 8.dp),
            color = Color.LightGray,
        )
    }
    Row {
        AdButton("LOAD", loadAd)
        AdButton("SHOW", showAd, enabled)
    }
}

/**
 * A button to load and show an ad.
 *
 * @param text The text to display on the button.
 * @param onClick A function to call when the button is clicked.
 * @param enabled Whether the button should be enabled. It is disabled by default, and is enabled when the ad is loaded.
 */
@Composable
fun AdButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    val context = LocalContext.current
    val color = remember { Color(ContextCompat.getColor(context, R.color.chartboost_green)) }

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = Color.White,
            ),
    ) {
        Text(text = text)
    }
}

/**
 * A toggle to enable or disable the fullscreen API for interstitial and rewarded ads. It is enabled by default
 * and effective immediately upon change.
 *
 * @param settingKey The key to use to store the setting in SharedPreferences.
 * @param context The context to use to get the SharedPreferences.
 * @param onSettingChange A function to call when the setting is changed.
 */
@Composable
fun FullscreenApiToggle(
    settingKey: String,
    context: Context,
    onSettingChange: (Boolean) -> Unit,
) {
    val sharedPref =
        context.getSharedPreferences(
            "com.chartboost.mediation.sdk.demo",
            Context.MODE_PRIVATE,
        )
    var toggleState by remember {
        mutableStateOf(sharedPref.getBoolean(settingKey, true))
    }

    Row(
        modifier =
            Modifier
                .toggleable(
                    role = Role.Switch,
                    value = toggleState,
                    onValueChange = { newValue ->
                        toggleState = newValue
                        sharedPref
                            .edit()
                            .putBoolean(settingKey, newValue)
                            .apply()
                        onSettingChange(newValue)
                    },
                )
                .padding(top = 4.dp, bottom = 4.dp, end = 0.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "Use Fullscreen API", modifier = Modifier.padding(end = 8.dp))
        Switch(
            checked = toggleState,
            onCheckedChange = null,
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor =
                        Color(
                            ContextCompat.getColor(
                                context,
                                R.color.white,
                            ),
                        ),
                    checkedTrackColor =
                        Color(
                            ContextCompat.getColor(
                                context,
                                R.color.chartboost_green,
                            ),
                        ),
                ),
        )
    }
}

/**
 * A toggle to enable or disable the fullscreen API for interstitial and rewarded ads. It is enabled by default
 * and effective immediately upon change.
 *
 * @param settingKey The key to use to store the setting in SharedPreferences.
 * @param context The context to use to get the SharedPreferences.
 * @param onSettingChange A function to call when the setting is changed.
 */
@Composable
fun FullscreenAdQueueToggle(
    settingKey: String,
    context: Context,
    shouldUseFullscreenApi: Boolean,
    onSettingChange: (Boolean) -> Unit,
) {
    val sharedPref =
        context.getSharedPreferences(
            "com.chartboost.mediation.sdk.demo",
            Context.MODE_PRIVATE,
        )
    var toggleState by remember {
        mutableStateOf(sharedPref.getBoolean(settingKey, false))
    }

    Row(
        modifier =
            Modifier
                .toggleable(
                    role = Role.Switch,
                    value = toggleState,
                    onValueChange = { newValue ->
                        toggleState = newValue
                        sharedPref
                            .edit()
                            .putBoolean(settingKey, newValue)
                            .apply()
                        onSettingChange(newValue)
                    },
                )
                .padding(top = 4.dp, end = 0.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "Use Fullscreen Ad Queue", modifier = Modifier.padding(end = 8.dp))
        Switch(
            checked = toggleState,
            onCheckedChange = null,
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor =
                        Color(
                            ContextCompat.getColor(
                                context,
                                R.color.white,
                            ),
                        ),
                    checkedTrackColor =
                        Color(
                            ContextCompat.getColor(
                                context,
                                R.color.chartboost_green,
                            ),
                        ),
                ),
        )
    }
}

/**
 * A console to display log messages.
 *
 * @param logs The list of log messages to display.
 */
@Composable
fun LogConsole(logs: MutableList<String>) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val buttonColor = remember { Color(ContextCompat.getColor(context, R.color.chartboost_green)) }

    // Scroll to the bottom of the list when the list is updated.
    LaunchedEffect(key1 = logs.size) {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.let {
            listState.scrollToItem(logs.size - 1)
        }
    }

    Column {
        LazyColumn(
            state = listState,
            modifier =
                Modifier
                    .padding(top = 16.dp)
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(350.dp),
        ) {
            items(logs) { log ->
                Text(log, modifier = Modifier.padding(8.dp))
            }
        }

        // Move button to right side
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    logs.clear()
                },
                modifier =
                    Modifier
                        .padding(top = 16.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = Color.White,
                    ),
            ) {
                Text(text = "Clear Logs")
            }
        }
    }
}

/**
 * A banner ad wrapped in a Compose view. The ad is loaded upon creation and shown automatically.
 *
 * @param placementName The name of the placement to load and show.
 * @param logState A list of log messages to display in the log console.
 */
@Composable
fun HeliumBannerAdCompose(
    placementName: String,
    logState: MutableList<String>,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
    ) {
        AndroidView(
            factory = { context ->
                val banner =
                    AdController.loadBannerAd(
                        context,
                        placementName,
                        HeliumBannerAd.HeliumBannerSize.STANDARD,
                        logState,
                    )

                val relativeLayout =
                    RelativeLayout(context).apply {
                        layoutParams =
                            RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                            )
                    }

                relativeLayout.addView(banner)
                relativeLayout
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
