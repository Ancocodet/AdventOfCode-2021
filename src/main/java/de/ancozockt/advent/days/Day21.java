package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.AdventDay;
import de.ancozockt.advent.utilities.ADay;

import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@ADay(day = "day21")
public class Day21 implements AdventDay {

    public record Result(long player1, long player2) {

    }

    public record GameState(long p1Score, long p2Score, long p1Pos, long p2Pos, boolean p1Turn) {

    }

    @Override
    public String part1(BufferedReader reader) {
        Set<Player> players = formatInput(reader);
        Dice dice = new Dice();

        Queue<Player> playerQueue = new ArrayDeque<>();
        Player playerOne = players.stream().filter(player -> { return player.name.equalsIgnoreCase("Player 1");}).findFirst().get();
        Player playerTwo = players.stream().filter(player -> { return player.name.equalsIgnoreCase("Player 2");}).findFirst().get();
        playerQueue.add(playerOne);
        playerQueue.add(playerTwo);

        while (!isFinished(players)){
            Player player = playerQueue.poll();
            int throwValue = 0;
            for(int i = 0; i < 3; i++){
                throwValue += dice.throwDice();
            }
            int newPosition = player.position().get() + throwValue;
            while (newPosition > 10){
                newPosition -= 10;
            }
            player.position().set(newPosition);
            player.addPoints(player.position().get());

            playerQueue.add(player);
        }

        Player loser = getLoser(players);
        return String.valueOf(loser.getPoints() * dice.getCount());
    }

    @Override
    public String part2(BufferedReader reader) {
        Set<Player> players = formatInput(reader);
        Player playerOne = players.stream().filter(player -> { return player.name.equalsIgnoreCase("Player 1");}).findFirst().get();
        Player playerTwo = players.stream().filter(player -> { return player.name.equalsIgnoreCase("Player 2");}).findFirst().get();

        Result res = game(new HashMap<>(), 0, 0, playerOne.position.get()-1, playerTwo.position.get()-1, true);
        return String.valueOf(Math.max(res.player1, res.player2));
    }

    private Player getLoser(Set<Player> players){
        return players.stream().filter(player -> {
            return player.getPoints() < 1000;
        }).findFirst().get();
    }

    private boolean isFinished(Set<Player> players){
        return players.stream().filter(player -> {
            return player.getPoints() >= 1000;
        }).toList().size() > 0;
    }

    private Set<Player> formatInput(BufferedReader reader){
        Set<Player> players = new HashSet<>();

        reader.lines().forEach(line -> {
            String position = line.split(": ")[1];
            String name = line.split(" ")[0] + " " + line.split(" ")[1];
            players.add(new Player(name, new AtomicInteger(Integer.parseInt(position)), new AtomicInteger(0)));
        });

        return players;
    }

    public record Player(String name, AtomicInteger position, AtomicInteger points){

        public void addPoints(int addition){
            this.points.getAndAdd(addition);
        }

        public int getPoints(){
            return points.get();
        }

    }

    public static class Dice{

        private final AtomicInteger counter = new AtomicInteger(0);
        private final AtomicInteger current = new AtomicInteger(1);

        public int throwDice(){
            counter.getAndIncrement();
            if(current.get() > 100){
                current.set(1);
            }
            return current.getAndIncrement();
        }

        public int getCount(){
            return counter.get();
        }
    }

    private Result game(Map<GameState, Result> memo, long p1Score, long p2Score, long p1Pos, long p2Pos, boolean p1Turn) {
        if (p1Score >= 21) {
            return new Result(1, 0);
        } else if (p2Score >= 21) {
            return new Result(0, 1);
        }

        GameState gs = new GameState(p1Score, p2Score, p1Pos, p2Pos, p1Turn);

        if (memo.containsKey(gs)) {
            return memo.get(gs);
        }

        Result res3, res4, res5, res6, res7, res8, res9;

        if (p1Turn) {
            res3 = game(memo, p1Score + 1 + ((p1Pos + 3) % 10), p2Score, (p1Pos + 3) % 10, p2Pos, false);
            res4 = game(memo, p1Score + 1 + ((p1Pos + 4) % 10), p2Score, (p1Pos + 4) % 10, p2Pos, false);
            res5 = game(memo, p1Score + 1 + ((p1Pos + 5) % 10), p2Score, (p1Pos + 5) % 10, p2Pos, false);
            res6 = game(memo, p1Score + 1 + ((p1Pos + 6) % 10), p2Score, (p1Pos + 6) % 10, p2Pos, false);
            res7 = game(memo, p1Score + 1 + ((p1Pos + 7) % 10), p2Score, (p1Pos + 7) % 10, p2Pos, false);
            res8 = game(memo, p1Score + 1 + ((p1Pos + 8) % 10), p2Score, (p1Pos + 8) % 10, p2Pos, false);
            res9 = game(memo, p1Score + 1 + ((p1Pos + 9) % 10), p2Score, (p1Pos + 9) % 10, p2Pos, false);
        } else {
            res3 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 3) % 10), p1Pos, (p2Pos + 3) % 10, true);
            res4 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 4) % 10), p1Pos, (p2Pos + 4) % 10, true);
            res5 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 5) % 10), p1Pos, (p2Pos + 5) % 10, true);
            res6 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 6) % 10), p1Pos, (p2Pos + 6) % 10, true);
            res7 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 7) % 10), p1Pos, (p2Pos + 7) % 10, true);
            res8 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 8) % 10), p1Pos, (p2Pos + 8) % 10, true);
            res9 = game(memo, p1Score, p2Score + 1 + ((p2Pos + 9) % 10), p1Pos, (p2Pos + 9) % 10, true);
        }

        Result res = new Result(
                res3.player1
                        + 3 * res4.player1
                        + 6 * res5.player1
                        + 7 * res6.player1
                        + 6 * res7.player1
                        + 3 * res8.player1
                        + res9.player1,
                res3.player2
                        + 3 * res4.player2
                        + 6 * res5.player2
                        + 7 * res6.player2
                        + 6 * res7.player2
                        + 3 * res8.player2
                        + res9.player2);
        memo.put(gs, res);

        return res;
    }
}
