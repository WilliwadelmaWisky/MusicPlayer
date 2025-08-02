# Music Player
![Version](https://img.shields.io/badge/java-21+-blue.svg?style=flat)
![License](https://img.shields.io/badge/license-MIT-green.svg?style=flat)

<p align="justify">
    A simple cross-platform music player application built with java on top of javafx framework. The application is very simple to use and has a very similar interface to some well known options. In fact a lot of inspiration in the interface has been drawn from the these options. The application allows the user to create persistent playlists and to play them at will. The application contains all the basic functionality to be expected of a music player.
</p>

<p align="justify">
    This functionality includes, for example, a few different modes of playing through the playlist (ordered, randomized, repeated), skip a song, jump to a certain timestamp and change the volume. There is also an option to play songs just by opening a file/directory and play the song(s) directly without the need of creating a playlist. The application also contains an extremely simple custom json parser. This helps with exporting and importing playlists in an easy-to-manage format.
</p>

Libraries used to make the application:
- ControlsFX
- BootstrapFX

> [!IMPORTANT]
> The application realies on JavaFX MediaPlayer which causes some problems in some linux distributions (at least on Debian based). These problems arise when the MediaPlayer is trying to read the contents of the `.mp3` file. To solve this issue you may want to consider converting the `.mp3` files to `.wav` using something like ffmpeg.

<p align="right">(<a href="#music-player">back to top</a>)</p>

## Usage

...

*For more examples and usage, please refer to the Wiki.*

<p align="right">(<a href="#music-player">back to top</a>)</p>

## Getting Started
> [!NOTE]
> The application is in usable state but there is currently no built jar file as the application is missing tests.

### Prerequisites
- Install Java 21+

### Installation
1. Download the desired version of the application from releases. A zip directory is downloaded
2. Unzip the downloaded directory to a location of your choosing. The directory contains `MusicPlayer-v0.1.0.jar` file (and some other files, license etc.), where the 0.1.0 is the version number and may wary.
3. Run the `MusicPlayer-v0.1.0.jar` file either by double-clicking or by running the command below.
    ```sh
    java -jar MusicPlayer-v0.1.0.jar
    ```

<p align="right">(<a href="#music-player">back to top</a>)</p>

## License
Distributed under the MIT License. See <a href="./LICENSE">LICENSE</a> for more information.

<p align="right">(<a href="#music-player">back to top</a>)</p>

## Version History

- 0.1
    - Initial release

<p align="right">(<a href="#music-player">back to top</a>)</p>
