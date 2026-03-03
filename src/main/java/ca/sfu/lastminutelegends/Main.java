package ca.sfu.lastminutelegends;

public class Main {
    public static void main(String[] args) {
        Game game = Game.instance();
        game.load();
        game.loop();
    }
}
