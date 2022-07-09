package Jade;

import components.BoxPhysics;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class Tilemap extends GameObject{

    //parse spritesheet based on a file taken from scene that this was created in
    //load tiles into an array of tiles to access and use in "scene"

    //**each tile must also render to the screen... duh

    public Scene scene;
    private static final int TILE_SIZE = 16;
    private Spritesheet spritesheet;
    private String vals;
    private int[] tiles;

    private int w,h;

    public Tilemap(Spritesheet spritesheet, String values, int w, int h){
        super("Tilemap");
        this.spritesheet = spritesheet;
        this.vals = values;
        this.w = w;//num tiles wide
        this.h = h;//num tiles high

    }

    @Override
    public void start(){
        this.tiles = loadTileArray(vals);
        createTiles(tiles);

    }


    private int[] loadTileArray(String valuesFile){
        String line = "";
        String[] data = new String[0];
        try
        {
            //I want this in some kind of reader
            BufferedReader br = new BufferedReader(new FileReader(vals));
            while ((line = br.readLine()) != null)
            {
                data = line.split(",");

            }


            this.tiles = new int[data.length];
            for (int i=0; i< data.length; i++){
                this.tiles[i] = parseInt(data[i]);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            tiles = new int[0];
            System.err.println("TILES WERE NOT LOADED");
        }

        return tiles;
    }

    private void createTiles(int[] tileArray){

        int index=0;
        for(int i=0; i< tileArray.length / w; i++){
            for(int j=0; j<tileArray.length / h; j++) {//this nested loop creates all tiles

                if (tileArray[index] != -1) {
                    GameObject newTile = new GameObject("Sprite: " + i+","+j, new Transform(new Vector2f(j * TILE_SIZE, (h-1)*TILE_SIZE - (i * TILE_SIZE)), new Vector2f(TILE_SIZE, TILE_SIZE)), 0);

                    Sprite spr = spritesheet.getSprite(tileArray[index]);
                    newTile.addComponent(new SpriteRenderer(spr));
                    this.scene.boxes.add(newTile.transform);

                    this.scene.addGameObjectToScene(newTile);

                }

                index++;

            }//end for j
        }//end for i
    }//end mthd









}
