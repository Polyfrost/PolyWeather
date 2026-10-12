# PolyWeather

[![Modrinth downloads](https://img.shields.io/modrinth/dt/r8Cr8YRg?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/polyweather)
[![CurseForge downloads](https://img.shields.io/curseforge/dt/1148002?style=flat&logo=curseforge&label=CurseForge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/polyweather)

PolyWeather sets the weather you see in your world. It only changes your own view, so the server and other players keep their own weather.

PolyWeather is off after you install it. Turn on "Enable PolyWeather" in the settings to use it.

## Features

- Pick clear, rain, storm or snow. PolyWeather shows the weather you pick in every biome, so it can rain in a desert and snow in a jungle.
- Set the rain, snow and thunder intensity, each from 0 to 1 in steps of 0.1. The default is 1.
- The sky, the clouds and the fog change color to match the weather you pick, and the sun and moon fade in rain.
- Storm darkens the sky. PolyWeather does not add lightning.
- With "Enable Sounds" on, rain sounds and rain splashes on the ground match the weather you pick. The option is on by default.
- Follow the real weather where you live. PolyWeather reads the hourly forecast and blends the intensity from one hour to the next.

## IRL weather

PolyWeather only loads the forecast if "Use IRL weather" is on when the game starts. Restart the game after you turn the option on.

If the forecast does not load, you see clear weather.

## Supported versions

- Minecraft 1.8.9 on Ornithe.
- Minecraft 1.21.1, 1.21.4, 1.21.5, 1.21.8, 1.21.10, 1.21.11, 26.1, 26.2 and 26.3 on Fabric.

PolyWeather needs [OneConfig](https://modrinth.com/mod/oneconfig) and Fabric Language Kotlin.

## Support

- Report bugs and ask questions on the [Polyfrost Discord](https://polyfrost.org/discord).
- Check [Polyfrost Status](https://status.polyfrost.org/) if a Polyfrost service is down.

## Building

```sh
./gradlew ":26.3:build"
```

Replace `26.3` with any supported Minecraft version. The build needs JDK 21 and JDK 25 installed.
