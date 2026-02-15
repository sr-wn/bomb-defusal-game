import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BombDefusalServer {

    private static BombDefusalGame game = new BombDefusalGame();

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new GameHandler());
        server.createContext("/countdown.mp3", new FileHandler());
        server.createContext("/mixkit-explosion-hit-1704.wav", new FileHandler());
        server.createContext("/jesuschristisgod-children-saying-yay-praise-and-worship-jesus-299607.mp3", new FileHandler());
        server.setExecutor(null);
        System.out.println("Bomb Defusal Game server running at http://localhost:" + port + "/");
        server.start();
    }

    static class GameHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            String method = exchange.getRequestMethod();

            if ("POST".equalsIgnoreCase(method) || ("GET".equalsIgnoreCase(method) && query != null)) {
                Map<String, String> params = parseQuery(query);

                if (params.containsKey("reset")) {
                    game.resetGame();
                } else if (params.containsKey("choice")) {
                    try {
                        int choice = Integer.parseInt(params.get("choice"));
                        int index = choice - 1;
                        game.checkWire(index);
                    } catch (NumberFormatException e) {
                        // ignore invalid
                    }
                }
            }

            String response = buildHtmlPage();
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }

        private Map<String, String> parseQuery(String query) {
            Map<String, String> result = new HashMap<>();
            if (query == null || query.isEmpty()) {
                return result;
            }
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    result.put(kv[0], kv[1]);
                }
            }
            return result;
        }

        private String buildHtmlPage() {
            StringBuilder html = new StringBuilder();

            html.append("<!DOCTYPE html>");
            html.append("<html lang='en'>");
            html.append("<head>");
            html.append("<meta charset='UTF-8'>");
            html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            html.append("<title>Bomb Defusal Game</title>");
            html.append("<style>");
            html.append("body { font-family: 'Segoe UI', Arial, sans-serif; background: radial-gradient(circle at top, #333 0, #000 60%); color: #f5f5f5; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }");
            html.append(".container { background: linear-gradient(145deg, #181818, #101010); padding: 24px 28px; border-radius: 14px; box-shadow: 0 0 25px rgba(0,0,0,0.8); max-width: 520px; width: 100%; border: 2px solid #333; position: relative; }");
            html.append(".led-strip { position: absolute; top: -8px; left: 16px; right: 16px; height: 6px; background: repeating-linear-gradient(90deg, #ff5555 0, #ff5555 10px, #ffaa00 10px, #ffaa00 20px, #00cc66 20px, #00cc66 30px); box-shadow: 0 0 8px rgba(255,85,85,0.7); border-radius: 4px; }");
            html.append("h1 { text-align: center; color: #ff5555; margin-bottom: 4px; letter-spacing: 2px; text-transform: uppercase; }");
            html.append("h2 { text-align: center; color: #ffaa00; margin-top: 0; font-size: 0.95rem; font-weight: normal; }");
            html.append("p { line-height: 1.5; font-size: 0.95rem; }");
            html.append(".story { margin: 12px 0 14px; padding: 10px 12px; border-radius: 8px; background: #181818; border: 1px solid #333; font-size: 0.9rem; }");
            html.append(".timer-panel { display: flex; justify-content: space-between; align-items: center; margin-top: 8px; padding: 8px 10px; border-radius: 8px; background: #141414; border: 1px solid #333; }");
            html.append(".timer-screen { font-family: 'Courier New', monospace; font-size: 1.1rem; color: #ff4444; background: #000; padding: 6px 10px; border-radius: 4px; box-shadow: 0 0 8px rgba(255,0,0,0.6) inset; }");
            html.append(".attempts-label { font-size: 0.9rem; color: #cccccc; }");
            html.append(".wires { margin: 18px 0 8px; display: grid; grid-template-columns: 1fr; gap: 8px; }");
            html.append(".wire-form { margin: 0; }");
            html.append(".wire-button { width: 100%; text-align: left; background: #1a1a1a; border: 1px solid #333; padding: 8px 10px; border-radius: 6px; cursor: pointer; display: flex; align-items: center; justify-content: space-between; transition: transform 0.08s ease, box-shadow 0.08s ease, background 0.08s ease; font-size: 0.95rem; }");
            html.append(".wire-button:hover { background: #262626; box-shadow: 0 0 8px rgba(255,255,255,0.1); transform: translateY(-1px); }");
            html.append(".wire-label { font-weight: 500; }");
            html.append(".wire-color-dot { width: 14px; height: 14px; border-radius: 50%; margin-left: 8px; box-shadow: 0 0 6px rgba(0,0,0,0.6); border: 1px solid #000; }");
            html.append(".status { margin-top: 14px; padding: 10px 12px; border-radius: 8px; background: #181818; border: 1px solid #333; font-size: 0.9rem; }");
            html.append(".hint { color: #66ccff; margin-top: 6px; }");
            html.append(".attempts { font-weight: bold; margin-top: 12px; }");
            html.append("button, input[type='submit'] { background: linear-gradient(135deg, #ff5555, #ff0000); color: white; border: none; padding: 9px 16px; border-radius: 5px; cursor: pointer; margin-top: 12px; font-weight: 600; letter-spacing: 0.5px; }");
            html.append("button:hover, input[type='submit']:hover { background: linear-gradient(135deg, #ff7777, #ff2222); }");
            html.append(".play-again { margin-top: 12px; }");
            html.append(".footer { margin-top: 12px; font-size: 0.75rem; text-align: center; color: #777; }");
            html.append(".intro { animation: fadeIn 1.5s ease-in; }");
            html.append("@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }");
            html.append(".flash-wrong { animation: flashRed 0.5s; }");
            html.append("@keyframes flashRed { 0% { background: #1a1a1a; } 50% { background: #660000; } 100% { background: #1a1a1a; } }");
            html.append(".flash-correct { animation: flashGreen 0.6s; }");
            html.append("@keyframes flashGreen { 0% { background: #1a1a1a; } 50% { background: #004400; } 100% { background: #1a1a1a; } }");
            html.append(".result-message { margin-top: 10px; padding: 10px; border-radius: 6px; font-weight: 500; }");
            html.append(".result-success { background: #003300; color: #00ff00; border: 1px solid #006600; }");
            html.append(".result-fail { background: #330000; color: #ff5555; border: 1px solid #660000; }");
            html.append("</style>");
            html.append("</head>");
            html.append("<body>");
            html.append("<div class='container intro'>");
            // Audio elements
            html.append("<audio id='countdownAudio' loop>");
            html.append("<source src='countdown.mp3' type='audio/mpeg'>");
            html.append("</audio>");
            html.append("<audio id='explosionAudio'>");
            html.append("<source src='mixkit-explosion-hit-1704.wav' type='audio/wav'>");
            html.append("</audio>");
            html.append("<audio id='successAudio'>");
            html.append("<source src='jesuschristisgod-children-saying-yay-praise-and-worship-jesus-299607.mp3' type='audio/mpeg'>");
            html.append("</audio>");
            html.append("<div class='led-strip'></div>");
            html.append("<h1>Bomb Defusal Game</h1>");
            html.append("<h2>A live device is armed. Decode the story to find the safe wire.</h2>");
            // Start button to trigger audio
            html.append("<div id='startScreen'>");
            html.append("<button id='startBtn' onclick='startGame()' style='margin:20px auto; display:block; padding:10px 20px; font-size:1rem; background:#ff5555; color:white; border:none; border-radius:5px; cursor:pointer;'>Start Game</button>");
            // Debug audio test buttons
            html.append("<div style='margin-top:20px; text-align:center;'>");
            html.append("<button onclick='document.getElementById(\"countdownAudio\").play()' style='margin:2px; padding:5px 10px; font-size:0.8rem;'>Test Countdown</button>");
            html.append("<button onclick='document.getElementById(\"explosionAudio\").play()' style='margin:2px; padding:5px 10px; font-size:0.8rem;'>Test Explosion</button>");
            html.append("<button onclick='document.getElementById(\"successAudio\").play()' style='margin:2px; padding:5px 10px; font-size:0.8rem;'>Test Success</button>");
            html.append("</div>");
            html.append("</div>");
            html.append("<div id='gameScreen' style='display:none;'>");

            String story = game.getStory();
            if (story != null && !story.isEmpty()) {
                html.append("<div class='story'>" + escapeHtml(story) + "</div>");
            }

            html.append("<div class='timer-panel'>");
            html.append("<div class='attempts-label'>Time Left Before Blast</div>");
            long secondsLeft = game.timeLeftSeconds();
            String timerColor = (secondsLeft <= 10 && secondsLeft > 0) ? "#ffaa00" : "#ff4444";
            String timerText = game.isGameOver() ? "000" : String.format("%02d", secondsLeft);
            html.append("<div class='timer-screen' style='color:" + timerColor + ";'>" + timerText + "</div>");
            html.append("</div>");

            html.append("<div class='wires'>");

            String[] wires = game.getWires();
            for (int i = 0; i < wires.length; i++) {
                int number = i + 1;
                String colorName = wires[i];
                String colorStyle;
                switch (colorName) {
                    case "Red":
                        colorStyle = "background: #cc0000;";
                        break;
                    case "Blue":
                        colorStyle = "background: #0066cc;";
                        break;
                    case "Green":
                        colorStyle = "background: #00aa33;";
                        break;
                    case "Yellow":
                        colorStyle = "background: #ffcc00;";
                        break;
                    case "Black":
                        colorStyle = "background: #000000;";
                        break;
                    default:
                        colorStyle = "background: #888888;";
                        break;
                }

                html.append("<form class='wire-form' method='GET' action='/'>");
                html.append("<input type='hidden' name='choice' value='" + number + "'>");
                if (game.isGameOver()) {
                    html.append("<button type='button' class='wire-button' disabled>");
                } else {
                    html.append("<button type='submit' class='wire-button'>");
                }
                html.append("<span class='wire-label'>" + number + ". " + escapeHtml(colorName) + "</span>");
                html.append("<span class='wire-color-dot' style='" + colorStyle + "'></span>");
                html.append("</button>");
                html.append("</form>");
            }

            if (game.isGameOver()) {
                html.append("<div class='attempts'>Game over. Attempts left: " + game.getAttemptsLeft() + "</div>");
            }

            html.append("</div>");

            html.append("<div class='status'>");
            String msg = game.getLastMessage();
            String hint = game.getLastHint();
            if (msg != null && !msg.isEmpty()) {
                if (game.isBombDefused()) {
                    html.append("<div class='result-message result-success'>" + escapeHtml(msg) + "</div>");
                } else if (game.isGameOver()) {
                    html.append("<div class='result-message result-fail'>" + escapeHtml(msg) + "</div>");
                } else {
                    html.append("<div class='flash-wrong'>" + escapeHtml(msg) + "</div>");
                }
            }
            if (hint != null && !hint.isEmpty()) {
                html.append("<div class='hint'>Hint: " + escapeHtml(hint) + "</div>");
            }
            // Timer-up message container (updated by JS when time runs out)
            html.append("<div id='timer-up-message' style='display:none;' class='result-message result-fail'>The bomb blasted! You were too late.</div>");
            html.append("</div>");

            if (game.isGameOver()) {
                html.append("<div class='play-again'>");
                html.append("<form method='GET' action='/'>");
                html.append("<input type='hidden' name='reset' value='1'>");
                html.append("<input type='submit' value='Play Again'>");
                html.append("</form>");
                html.append("</div>");
            }

            html.append("<div class='footer'>Decode the story. Choose the correct wire. Save the city.</div>");
            html.append("</div>"); // close gameScreen
            html.append("<script>");
            html.append("var secondsLeft = " + secondsLeft + ";");
            html.append("var timerInterval = null;");
            html.append("var countdownAudio, explosionAudio, successAudio;");
            html.append("function startGame() {");
            html.append("  var startScreen = document.getElementById('startScreen');");
            html.append("  var gameScreen = document.getElementById('gameScreen');");
            html.append("  if (!startScreen || !gameScreen) { console.error('Elements not found'); return; }");
            html.append("  startScreen.style.display = 'none';");
            html.append("  gameScreen.style.display = 'block';");
            html.append("  countdownAudio = document.getElementById('countdownAudio');");
            html.append("  explosionAudio = document.getElementById('explosionAudio');");
            html.append("  successAudio = document.getElementById('successAudio');");
            html.append("  if (countdownAudio) { countdownAudio.play().catch(e=>console.log('Countdown play failed:', e)); }");
            html.append("  startTimer();");
            html.append("}");
            html.append("function startTimer() {");
            html.append("  if (timerInterval) clearInterval(timerInterval);");
            html.append("  timerInterval = setInterval(updateTimer, 1000);");
            html.append("  updateTimer();");
            html.append("}");
            html.append("function stopTimer(showMessage, isSuccess) {");
            html.append("  if (timerInterval) { clearInterval(timerInterval); timerInterval = null; }");
            html.append("  var timerScreen = document.querySelector('.timer-screen');");
            html.append("  if (timerScreen) { timerScreen.textContent = '00'; timerScreen.style.color = '#ff4444'; timerScreen.style.animation = 'none'; }");
            html.append("  if (showMessage) {");
            html.append("    var msg = document.getElementById('timer-up-message');");
            html.append("    if (msg) msg.style.display = 'block';");
            html.append("    var wires = document.querySelectorAll('.wire-button');");
            html.append("    for (var i = 0; i < wires.length; i++) { wires[i].disabled = true; }");
            html.append("    var playAgain = document.querySelector('.play-again');");
            html.append("    if (playAgain) playAgain.style.display = 'block';");
            html.append("  }");
            html.append("  if (countdownAudio) { countdownAudio.pause(); countdownAudio.currentTime = 0; }");
            html.append("  if (isSuccess) {");
            html.append("    if (successAudio) { successAudio.play().catch(e=>console.log('Success play failed:', e)); }");
            html.append("  } else {");
            html.append("    if (explosionAudio) { explosionAudio.play().catch(e=>console.log('Explosion play failed:', e)); }");
            html.append("  }");
            html.append("}");
            html.append("function updateTimer() {");
            html.append("  var timerScreen = document.querySelector('.timer-screen');");
            html.append("  if (!timerScreen) return;");
            html.append("  if (secondsLeft <= 0) { stopTimer(true, false); return; }");
            html.append("  var display = secondsLeft < 10 ? '0' + secondsLeft : String(secondsLeft);");
            html.append("  timerScreen.textContent = display;");
            html.append("  if (secondsLeft <= 5) {");
            html.append("    timerScreen.style.color = '#ff0000';");
            html.append("    timerScreen.style.animation = 'pulse 0.5s infinite';");
            html.append("  } else if (secondsLeft <= 10) {");
            html.append("    timerScreen.style.color = '#ffaa00';");
            html.append("    timerScreen.style.animation = 'none';");
            html.append("  } else {");
            html.append("    timerScreen.style.color = '#ff4444';");
            html.append("    timerScreen.style.animation = 'none';");
            html.append("  }");
            html.append("  secondsLeft--;");
            html.append("}");
            // If already game over, show game screen and stop timer
            if (game.isGameOver()) {
                html.append("window.onload = function(){");
                html.append("  console.log('Game over on load, isBombDefused=" + game.isBombDefused() + "');");
                html.append("  document.getElementById('startScreen').style.display = 'none';");
                html.append("  document.getElementById('gameScreen').style.display = 'block';");
                html.append("  var successAudio = document.getElementById('successAudio');");
                html.append("  var explosionAudio = document.getElementById('explosionAudio');");
                html.append("  if (" + game.isBombDefused() + ") {");
                html.append("    console.log('Playing success audio on load');");
                html.append("    if (successAudio) successAudio.play().catch(e=>console.log('Success play failed on load:', e));");
                html.append("  } else {");
                html.append("    console.log('Playing explosion audio on load');");
                html.append("    if (explosionAudio) explosionAudio.play().catch(e=>console.log('Explosion play failed on load:', e));");
                html.append("  }");
                html.append("  stopTimer(false, " + game.isBombDefused() + ");");
                html.append("};");
            } else {
                // During game, if bomb defused, play success audio
                if (game.isBombDefused()) {
                    html.append("console.log('Bomb defused during game, playing success audio');");
                    html.append("stopTimer(false, true);");
                }
            }
            html.append("</script>");
            html.append("<style>");
            html.append("@keyframes pulse {");
            html.append("  0%, 100% { transform: scale(1); }");
            html.append("  50% { transform: scale(1.1); }");
            html.append("}");
            html.append("</style>");

            html.append("</div>");
            html.append("</body>");
            html.append("</html>");

            return html.toString();
        }

        private String escapeHtml(String text) {
            if (text == null) return "";
            return text.replace("&", "&amp;")
                       .replace("<", "&lt;")
                       .replace(">", "&gt;")
                       .replace("\"", "&quot;")
                       .replace("'", "&#39;");
        }
    }

    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            File file = new File("." + path);
            if (!file.exists() || !file.isFile()) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
            String contentType = "application/octet-stream";
            if (path.endsWith(".mp3")) contentType = "audio/mpeg";
            else if (path.endsWith(".wav")) contentType = "audio/wav";
            exchange.getResponseHeaders().set("Content-Type", contentType);
            long fileLength = file.length();
            exchange.sendResponseHeaders(200, fileLength);
            try (OutputStream os = exchange.getResponseBody(); FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
            }
        }
    }
}
