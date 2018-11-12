package ar.edu.itba.multiagent.pacman;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import ar.edu.itba.multiagent.pacman.environment.PositionUtils;
import ar.edu.itba.multiagent.pacman.player.Player;
import ar.edu.itba.multiagent.pacman.states.MoveState;
import com.badlogic.gdx.math.GridPoint2;
import com.typesafe.config.Config;
import org.jfree.ui.RefineryUtilities;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class StatisticManager {
    float timer;
    List<Float> timeSearching;
    List<Float> timeChasing;
    List<Boolean> isChasing;
    List<List<Float>> distanceToPacman;

    List<Float> visibilityRatio;
    GameManager gm;
    MoveState currentState;
    Float currentTime;

    Integer mapSize;
    Integer mapBlocks;
    Integer mapFree;
    List<Integer> viewedTiles;
    Config c;
    LineChart lc;
    boolean realTimePlot = false;

    public StatisticManager(GameManager gm, Config c) {
        timeSearching = new ArrayList<>();
        timeChasing = new ArrayList<>();
        distanceToPacman = new ArrayList<>();
        visibilityRatio = new ArrayList<>();
        isChasing = new ArrayList<>();
        this.gm = gm;
        timer = 0;
        currentState = MoveState.SEARCH;
        currentTime = 0.0f;
        mapSize = mapBlocks = mapFree = 0;
        for (int i = 0; i < gm.getAgents().size(); i++) {
            distanceToPacman.add(new ArrayList<>());
        }
        scanMap();
        viewedTiles = new ArrayList<>();
        this.c = c;
        realTimePlot = c.getBoolean("real-time-plot");
        if (realTimePlot){
            lc = new LineChart(c);
            lc.pack();
            RefineryUtilities.centerFrameOnScreen(lc);
            lc.setVisible(true);
        }
    }

    public void update(float deltaTime){
        timer += deltaTime;
        List<Ghost> ghosts = gm.getAgents();
        MoveState curState = MoveState.SEARCH;
        for(Ghost g: ghosts){
            if (g.getChasing().equals(MoveState.CHASE)){
                curState = MoveState.CHASE;
            }
        }
        distancesToPacman(ghosts);
        stateTime(curState,deltaTime);
        viewingArea(ghosts);
    }


    public void stateTime(MoveState state , float deltaTime){
        currentTime+=deltaTime;
        switch (state){
            case CHASE:
                isChasing.add(true);
                break;
            case SEARCH:
                isChasing.add(false);
                break;
        }
        if (!currentState.equals(state)){
            switch(currentState){
                case CHASE:
                    currentState = MoveState.SEARCH;
                    timeChasing.add(new Float(currentTime));
                    break;
                case SEARCH:
                    currentState = MoveState.CHASE;
                    timeSearching.add(new Float(currentTime));
                    break;
            }
            currentTime = 0.0f;
        }
    }

    public void show(){
        summarizeTime();
    }

    public void summarizeTime(){
        finishStats();
        Float chasingTotal = timeChasing.stream().reduce(0f,(x,y) -> x + y);
        Float searchingTotal = timeSearching.stream().reduce(0f, (x, y) -> x + y);
        System.out.println("Total time was = " + timer + " other : " + (chasingTotal+searchingTotal));
        System.out.println("Total chasing time : " + chasingTotal +  " " + (chasingTotal/timer *100) + "%");
        System.out.println("Total searching time : " + searchingTotal + " " + (searchingTotal/timer*100) + "%");
        System.out.println("Average chasing time : " + chasingTotal / timeChasing.size());
        System.out.println("Average searching time : " + searchingTotal / timeSearching.size());
        System.out.println("ChaseTime moments : " + timeChasing.size());
        System.out.println("SearchTime moments : " + timeSearching.size());
        System.out.println("Starting distance : " + distanceToPacman.get(0).get(0));
        System.out.println("Map free : " + mapFree );
        System.out.println("Map blocked : " + mapBlocks);
        viewedTiles.forEach(v -> System.out.print(v + " -"));
        if(!realTimePlot) {
            lc = new LineChart(c);
            lc.pack();
            RefineryUtilities.centerFrameOnScreen(lc);
            lc.setVisible(true);
            lc.addResult(distanceToPacman, isChasing);
        }
    }

    public void finishStats(){
        distancesToPacman(gm.getAgents());
        switch (currentState){
            case SEARCH:
                timeSearching.add(new Float(currentTime));
                break;
            case CHASE:
                timeChasing.add(new Float(currentTime));
        }
    }

    public void distancesToPacman(List<Ghost> ghosts){
        Player player =gm.getPlayer();
        for(Ghost ghost : ghosts){
            Float distance = ghost.getPosition().dst(player.getPosition());
            distanceToPacman.get(ghost.getId()).add(distance/16);
            if(realTimePlot){
                lc.addSingleFrame(distance, ghost.getId(), distanceToPacman.get(ghost.getId()).size());
            }
        }
    }


    public void scanMap(){
        int height = gm.getWorld().getGm().getHEIGHT();
        int width = gm.getWorld().getGm().getWIDTH();
        mapSize = height * width;
        boolean[][] blocks = gm.getWorld().getGm().getBlocks();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(blocks[i][j]){
                    mapBlocks++;
                }else{
                    mapFree++;
                }
            }
        }
    }


    public void viewingArea(List<Ghost> ghosts){
        Set<GridPoint2> accounted = new LinkedHashSet<>();
        int total = 0;
        for(Ghost ghost : ghosts){
            List<Direction> invalidDirections = new ArrayList<>();
            if(!ghost.getVisibilityDirections().get(1))
                invalidDirections.add(PositionUtils.getInverseDirection(ghost.getDirection()));
            for(Direction dir : Direction.values()){
                if(!invalidDirections.contains(dir)) {
                    GridPoint2 curPos = new GridPoint2(2,4);
                    if(!accounted.contains(curPos)){
                        accounted.add(curPos);
                        total++;
                    }
                    boolean wall = false;
                    for (int i = 0; i < ghost.getVisibility() && !wall; i++) {
                        if(gm.getWorld().getGm().hasWall(curPos,dir.directionVector())){
                            wall = true;
                        }else{
                            curPos.add(dir.directionVector());
                            if(!accounted.contains(curPos)){
                                accounted.add(curPos);
                                total++;
                            }
                        }
                    }
                }
            }
        }
        viewedTiles.add(total);
    }



}

