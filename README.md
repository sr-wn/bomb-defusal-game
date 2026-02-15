# Bomb Defusal Game

A web-based bomb defusal game built with Java. Decode the story, choose the correct wire, and save the city before time runs out.

## Features

- **Immersive UI**: Bomb-themed interface with LED strips and animations.
- **Real-time Countdown Timer**: 30-second timer with live updates and pulse effects.
- **Scenario-based Stories**: Purely narrative clues—no visual hints—10+ variants per wire color.
- **Audio Feedback**: 
  - Countdown audio loops during gameplay.
  - Explosion sound on failure.
  - Success sound on defusal.
- **One Attempt Only**: No second chances—think carefully!
- **Responsive Design**: Works on desktop browsers.

## How to Play

1. Click **Start Game** to begin.
2. Read the story clue carefully—it hints at the safe wire color.
3. Before the timer hits zero, click the wire you think is safe.
4. If you’re right, the bomb is defused and you win.
5. If you’re wrong or time runs out, the bomb explodes.

## Wire Colors

- 🔴 **Red**
- 🔵 **Blue**
- 🟢 **Green**
- 🟡 **Yellow**
- ⚫ **Black`

## Tech Stack

- **Backend**: Java with `com.sun.net.httpserver.HttpServer`
- **Frontend**: HTML5, CSS3, JavaScript
- **Audio**: MP3/WAV via HTML5 `<audio>`
- **Game Logic**: Object-Oriented Design in Java

## Local Setup

### Prerequisites

- Java 17 or higher
- A web browser

### Steps

1. Clone this repository:
   ```bash
   git clone https://github.com/sr-wn/bomb-defusal-game.git
   cd bomb-defusal-game
   ```

2. Compile the Java files:
   ```bash
   javac BombDefusalGame.java BombDefusalServer.java
   ```

3. Run the server:
   ```bash
   java BombDefusalServer
   ```

4. Open your browser and go to:
   ```
   http://localhost:8080
   ```

## Audio Files

Make sure these audio files are in the project root for full experience:
- `countdown.mp3` – Countdown loop
- `mixkit-explosion-hit-1704.wav` – Explosion sound
- `jesuschristisgod-children-saying-yay-praise-and-worship-jesus-299607.mp3` – Success sound

## Deploy Online

### Replit (Recommended, Free & No Sleep)

1. Go to [replit.com](https://replit.com) → Create Repl → Java.
2. Upload all files or copy-paste the code.
3. In Shell:
   ```bash
   javac *.java
   java BombDefusalServer
   ```
4. Share the public URL provided by Replit.

### Heroku (Free Tier)

1. Install Heroku CLI and run:
   ```bash
   heroku login
   heroku create your-app-name
   git push heroku main
   heroku open
   ```

### Vercel (Docker)

1. Create a `Dockerfile` (see below).
2. Install Vercel CLI and run:
   ```bash
   vercel --prod
   ```

#### Dockerfile for Vercel
```dockerfile
FROM openjdk:17-slim
WORKDIR /app
COPY . .
EXPOSE 8080
CMD ["java", "BombDefusalServer"]
```

## Project Structure

```
bomb-defusal-game/
├── BombDefusalGame.java    # Game logic and story generation
├── BombDefusalServer.java  # HTTP server and HTML UI
├── Procfile                # Heroku process definition
├── system.properties       # Java runtime version for Heroku
├── .gitignore              # Git ignore patterns
├── README.md               # This file
├── countdown.mp3           # Countdown audio
├── mixkit-explosion-hit-1704.wav
└── jesuschristisgod-children-saying-yay-praise-and-worship-jesus-299607.mp3
```

## Contributing

Contributions are welcome! Feel free to submit issues or pull requests.

## License

This project is open source and available under the [MIT License](LICENSE).

---

🎮 **Play smart. Stay calm. Defuse the bomb!**
