<h1>Football World Cup Score Board</h1>

Is a simple library delivered abstraction like <b>ScoredBoard</b> and <b>ScoreBoardItem</b>

The main idea of ​​this solution is to provide the following method:

``` java
class ScoreBoard(){
  public void startGame(String homeTeam, String awayTeam);
  public void finishGame(String homeTeam, String awayTeam);
  public boolean updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
  public Set<? extends ScoreBoardItem> getSummaryGames()
}
```
> *The game has current results, without a history of its changes*

> *Library aren't throw custom exception, because is simplised as much as possible. Messages are logging.*

📦 Build with:
- Java 17
- Maven (https://maven.apache.org/)

⚙️ How to use

Like library added to project.

Posible is running **main()** mathod from **Main.class** On this situtaion score bord will be printind in console. Method main() have something example code example below

``` java
public static void main(String[] args) throws InterruptedException {

    ScoreBoard scoreBoard = new ScoreBoard();

    // Poland - USA
    printComment("Start Poland vs USA");
    scoreBoard.startGame(POLAND, USA); // start Match Poland - USA
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("USA Gol");
    scoreBoard.updateScore(POLAND, USA, 0, 1); // Poland - USA 0 : 1 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("USA correct Gol");
    scoreBoard.updateScore(POLAND, USA, 0, 0); // Poland - USA 0 : 0 -- correct score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("USA Gol");
    scoreBoard.updateScore(POLAND, USA, 0, 1); // Poland - USA 0 : 1 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("USA Gol");
    scoreBoard.updateScore(POLAND, USA, 0, 2); // Poland - USA 0 : 2 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("USA Gol");
    scoreBoard.updateScore(POLAND, USA, 0, 3); // Poland - USA 0 : 3 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Poland Gol");
    scoreBoard.updateScore(POLAND, USA, 1, 3); // Poland - USA 1 : 3 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Poland Gol");
    scoreBoard.updateScore(POLAND, USA, 2, 3); // Poland - USA 2 : 3 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Poland Gol");
    scoreBoard.updateScore(POLAND, USA, 3, 3); // Poland - USA 3 : 3 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Start Mexico vs Uruguay");
    scoreBoard.startGame(MEXICO, URUGUAY); // start Match Mexico - Uruguay
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Poland Gol");
    scoreBoard.updateScore(POLAND, USA, 4, 1); // Poland - USA 4 : 3 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Mexico Gol");
    scoreBoard.updateScore(MEXICO, URUGUAY, 1, 0); // Mexico - Uruguay 1 : 0 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Mexico Gol");
    scoreBoard.updateScore(MEXICO, URUGUAY, 2, 0); // Mexico - Uruguay 2 : 0 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Finish Poland vs USA");
    scoreBoard.finishGame(POLAND, USA); // finish match Poland - USA
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Start Brazil vs Chile");
    scoreBoard.startGame(BRAZIL, CHILE); // start Match Brazil - Chile
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Brazil Gol");
    scoreBoard.updateScore(BRAZIL, CHILE, 1, 0); // Brazil - Chile 1 : 0 -- increase score
    printSummaryGames(scoreBoard);

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Brazil Gol");
    scoreBoard.updateScore(BRAZIL, CHILE, 2, 0); // Brazil - Chile 2 : 0 -- increase score
    printSummaryGames(scoreBoard);  // print Score Board

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Finish Mexico vs Uruguay");
    scoreBoard.finishGame(MEXICO, URUGUAY);
    printSummaryGames(scoreBoard);

    TimeUnit.SECONDS.sleep(SLEEP);
    printComment("Finish Brazil vs Chile");
    scoreBoard.finishGame(BRAZIL, CHILE);
    printSummaryGames(scoreBoard);  // print Score Board
}
```
