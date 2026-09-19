# CPS Display — Fabric mod

## Fastest way to get the .jar (no local install needed)

This project includes a GitHub Actions workflow (`.github/workflows/build.yml`)
that compiles the mod for you in the cloud:

1. Create a free account at [github.com](https://github.com) if you don't have one.
2. Create a new **public** repository (any name, e.g. `cpsmod`).
3. On the repo page, click **"uploading an existing file"** and drag in the
   entire contents of this folder (including the hidden `.github` folder —
   if your file browser hides it, use "Add file → Upload files" and select
   everything, or use GitHub Desktop instead of the web upload).
4. Go to the **Actions** tab of your repo. A build should start automatically
   (or click "Build CPS Mod" → "Run workflow" if it doesn't).
5. Once it finishes (green checkmark, ~2-3 minutes), click into the run and
   download the **cpsmod-jar** artifact under "Artifacts" — that's a zip
   containing your `.jar`.
6. Unzip it and drop the `.jar` straight into your `.minecraft/mods` folder.

No Java, no Gradle, nothing installed on your own machine.


Shows your current clicks-per-second (left and right mouse button, each
counted over a rolling 1-second window) in the top-left corner of the
screen while you play.

## Requirements

- Java 17 (JDK)
- Minecraft 1.20.1
- [Fabric Loader](https://fabricmc.net/use/) 0.15.0+
- [Fabric API](https://modrinth.com/mod/fabric-api) installed in your `mods` folder

## Building

From the project root:

```
./gradlew build
```

(On Windows use `gradlew.bat build`.) Gradle Loom will download everything
it needs on first run — no manual setup required, just an internet
connection.

The compiled mod jar will be at:

```
build/libs/cpsmod-1.0.0.jar
```

## Installing

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.20.1.
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) for 1.20.1 and
   drop the jar into your `.minecraft/mods` folder.
3. Copy `cpsmod-1.0.0.jar` into the same `mods` folder.
4. Launch Minecraft using the Fabric profile.

You should see "CPS: 0 (LMB)  0 (RMB)" in the top-left corner, updating live
as you click.

## How it works

- `MouseMixin` hooks into `Mouse.onMouseButton` (the raw GLFW callback) so
  every press is counted, not just in-game attacks/uses.
- `ClickTracker` keeps a timestamp queue per button and reports how many
  timestamps fall within the last 1000 ms — that count is your CPS.
- `CpsHud` draws the text every frame via Fabric API's `HudRenderCallback`.

## Customizing

- Move the counter: edit `X` / `Y` in `CpsHud.java`.
- Change the color: edit `COLOR` (standard 0xRRGGBB hex, e.g. `0xFF5555` for red).
- Change the averaging window: edit `WINDOW_MS` in `ClickTracker.java`
  (1000 = 1 second; a smaller value reacts faster but jitters more).
