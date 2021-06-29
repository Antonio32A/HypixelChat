const { username, password, auth } = require("../credentials.json");
const mineflayer = require("mineflayer");
const app = require("express")();
const http = require("http").Server(app);
const io = require("socket.io")(http, {
    cors: {
        origin: "*" // TODO add better cors handling and possibly authentication/password
    }
});

let bot;

const init = () => {
    bot = mineflayer.createBot({
        username, password, auth,
        host: "mc.hypixel.net",
        port: 25565,
        version: "1.12.2"
    });

    bot.once("login", async () => {
        log(`Logged in as ${bot.username}.`);
        await bot.chat("/achat §c");
        bot.once("spawn", () => console.log("Spawned in limbo."));
    });

    bot.once("end", () => {
        log("Disconnected.");
        init();
    });

    bot.once("kicked", (reason, loggedIn) => {
        log(`Exiting. Kicked ${loggedIn ? "after" : "before"} login for: ${reason}`);
        process.exit();
    });

    bot.on("message", (message, position) => {
        if (position === "game_info") return;
        console.log(message.toAnsi());
        io.sockets.emit("chat", message.toMotd());
    });
};

io.on("connection", socket => {
    log(socket.id + " has connected.");
    socket.on("chat", message => {
        if (bot)
            bot.chat(message);
    });
});

const log = message => {
    console.log(message);
    io.sockets.emit("alert", message);
};

http.listen(8936, init);
