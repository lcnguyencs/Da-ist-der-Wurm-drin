package GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.wurm.game.Wurm;

import Entities.Tick;
import Entities.Worm;
import Entities.Box;
import Entities.Dice;
import Manager.GameStateManager;

/**
 * This class represents the main game state. 
 * It contains the logic for handling user input, updating the game state, and rendering the game.
 */

public class PlayState implements Screen {
    public Wurm game;
    public OrthographicCamera gamecam;
    public Viewport gamePort;
    public TmxMapLoader mapLoader;
    public TiledMap map;
    public OrthogonalTiledMapRenderer renderer;
    public BitmapFont font;
    public SpriteBatch spriteBatch;
    public Texture sewage;
    public Texture sewage1;
    public Texture line1;
    public Texture line2;
    public Texture notiBox;
    public static Worm[] worms = new Worm[4];
    public Box[][] flowers = new Box[4][4];
    public Box[][] strawberries = new Box[4][4];
    public Tick[][] ticks = new Tick[4][4];
    public Dice dice;
    public boolean isMainState1 = false;
    public boolean isMainState2 = false;
    public boolean isMainState3 = false;
    public boolean isMoveBox1 = false;
    public boolean isMoveBox2 = false;
    public float countBonus1 = 0;
    public float countBonus2 = 0;
    public float textX, textY;
    public String currentPlayer, bonusMove = "";
    public BitmapFont turnFont, notiFont, nameFont;
    public float timeEndState;
    public GlyphLayout layout = new GlyphLayout();
    private GameStateManager gsm;

    /**
     * Constructor for the PlayState class. It initializes the GameStateManager and other necessary variables.
     *
     * @param game The current game instance.
     * @param gsm The GameStateManager for managing game states.
     */

    public PlayState(Wurm game, GameStateManager gsm) {
        this.gsm = gsm;
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Wurm.V_WIDTH, Wurm.V_HEIGHT, gamecam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("img/PlayState/backgroundPlayState.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(Wurm.V_WIDTH / 2, Wurm.V_HEIGHT / 2, 0);

        //load worms images
        worms[0] = new Worm("img/PlayState/worms/Little Gritty.png", Wurm.batch, -1170, 0, "Little Gritty", 0,false);
        worms[1] = new Worm("img/PlayState/worms/Lady Silver.png", Wurm.batch, -1170, 150, "Lady Silver", 0,false);
        worms[2] = new Worm("img/PlayState/worms/Rudy Red.png", Wurm.batch, -1170, 300, "Rudy Red", 0,false);
        worms[3] = new Worm("img/PlayState/worms/StripyToni.png", Wurm.batch, -1170, 450, "StripyToni", 0,false);

        //load sewage images
        sewage = new Texture("img/PlayState/sewages/Sewage.png");
        sewage1 = new Texture("img/PlayState/sewages/Sewage1.png");

        //load line between worms
        line1 = new Texture("img/PlayState/lines/line1.png");
        line2 = new Texture("img/PlayState/lines/line2.png");
        notiBox = new Texture("img/notiBox.png");

        //load dices
        dice = new Dice(Wurm.batch, 1160, 595);

        //load flowers and strawberries
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                flowers[i][0] = new Box("img/PlayState/flowers/flower0.png", Wurm.batch, 480, 43 + (150 * i), i, false);
                flowers[i][1] = new Box("img/PlayState/flowers/flower1.png", Wurm.batch, 480 + 75, 43 + (150 * i), i, false);
                flowers[i][2] = new Box("img/PlayState/flowers/flower2.png", Wurm.batch, 480 + 75 * 2, 43 + (150 * i), i, false);
                flowers[i][3] = new Box("img/PlayState/flowers/flower3.png", Wurm.batch, 480 + 75 * 3, 43 + (150 * i), i, false);

                strawberries[i][0] = new Box("img/PlayState/strawberries/strawberry0.png", Wurm.batch, 860, 43 + (150 * i), i, false);
                strawberries[i][1] = new Box("img/PlayState/strawberries/strawberry1.png", Wurm.batch, 860 + 75, 43 + (150 * i), i, false);
                strawberries[i][2] = new Box("img/PlayState/strawberries/strawberry2.png", Wurm.batch, 860 + 75 * 2, 43 + (150 * i), i, false);
                strawberries[i][3] = new Box("img/PlayState/strawberries/strawberry3.png", Wurm.batch, 860 + 75 * 3, 43 + (150 * i), i, false);

                ticks[i][j] = new Tick("img/PlayState/boxBlack.png", Wurm.batch, 480 + 75 * j, 43 + (150 * i), i, false);
            }
        }

        //font setting
        font = new BitmapFont();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal("font/KOMTXT__.ttf")
        );

        FreeTypeFontGenerator.FreeTypeFontParameter param_turnFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param_turnFont.size = 30;
        param_turnFont.borderWidth = 1;
        param_turnFont.borderColor = Color.BLACK;
        turnFont = generator.generateFont(param_turnFont);
        turnFont.setColor(Color.WHITE);

        FreeTypeFontGenerator.FreeTypeFontParameter param_notiFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param_notiFont.size = 40;
        param_notiFont.borderWidth = 1;
        param_notiFont.borderColor = Color.BLACK;
        notiFont = generator.generateFont(param_notiFont);
        notiFont.setColor(Color.RED);

        FreeTypeFontGenerator.FreeTypeFontParameter param_nameFont = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param_nameFont.size = 32;
        param_nameFont.borderWidth = 3;
        param_nameFont.borderColor = Color.BLACK;
        nameFont = generator.generateFont(param_nameFont);
        nameFont.setColor(Color.YELLOW);
    }

    @Override
    public void show() {
    }

    /**
     * Handles user input. If the left mouse button is clicked, it prints "Clicked" to the console.
     *
     * @param dt The time delta since the last frame.
     */
    public void handleInput(float dt) {
        // if (Gdx.input.isTouched()) {
        //     System.out.println("Clicked");
        // }
    }

     /**
     * Updates the game state.
     *
     * @param dt The time delta since the last frame.
     */
    public void update(float dt) {
        handleInput(dt);

        for (int i = 0; i < 4; i++) {
            worms[i].update();
        }
        dice.update();

        //update flowers and strawberries
        if (!isMoveBox1){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    flowers[i][j].update();
                }
            }
        }

        if (!isMoveBox2){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    strawberries[i][j].update();
                }
            }
        }
        //update camera
        gamecam.update();
        renderer.setView(gamecam);
    }

    /**
     * Renders the game state.
     *
     * @param delta The time delta since the last frame.
     */
    @Override
    public void render(float delta) {
        update(delta);
        //clear the screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        Wurm.batch.setProjectionMatrix(gamecam.combined);

        //begin render
        Wurm.batch.begin();

            //draw worms
            for (int i = 0; i < 4; i++) {
                worms[i].render();
            }
            //draw lines
            for (int i = 0; i < 4; i++) {
                Wurm.batch.draw(line1, 0, 150 * i);
                Wurm.batch.draw(line2, 0, 150 * i + 130);
            }
            //draw dice
            dice.render();
            //draw sewage
            for (int i=0; i<4; i++){
                Wurm.batch.draw(sewage, 80 + 20, 150*i);
                Wurm.batch.draw(sewage, 460 + 20, 150*i);
                Wurm.batch.draw(sewage1, 840 + 20, 150*i);
                //draw names
                nameFont.draw(Wurm.batch, worms[i].id, 155, 110 + 150 * i);
            }

            //draw notification box
            Wurm.batch.draw(notiBox, 365, 630);
            if (worms[0].point != 0){
                String turnText = worms[(dice.getTurn() + 3) % 4].id + " moved " + Integer.toString(Dice.currentNumber) + " moves!";
                layout.setText(notiFont, turnText);
                textX = 365 + (notiBox.getWidth() - layout.width) / 2;
                textY = 630 + (notiBox.getHeight() + layout.height) / 2;
                notiFont.draw(Wurm.batch,turnText,textX,textY);
            } else {
                String sologan = "Let's go!";
                layout.setText(notiFont, sologan);
                textX = 365 + (notiBox.getWidth() - layout.width) / 2;
                textY = 630 + (notiBox.getHeight() + layout.height) / 2;
                notiFont.draw(Wurm.batch,sologan,textX,textY);
            }

            //Switch games state 1 to 2
            if (worms[3].getPoint() != 0){
                isMainState1 = true;
            }
            for (int i = 0; i < 4; i++){
                if (worms[i].getPoint() > 20){
                    isMainState1 = false;
                    isMainState2 = true;
                    if (!isMoveBox1){
                        for (int j = 0; j < 4; j++){
                            //update bonus
                            if (ticks[i][j].status){
                                worms[j].move(3);
                                worms[j].bonus = true;
                            }
                        }
                        for (int j = 0; j < 4; j ++){
                            for (int k = 0 ; k < 4; k++) {
                                ticks[j][k].status = false;
                                ticks[j][k].x += 380;
                            }
                        }
                        isMoveBox1 = true;
                    }
                }
            }

            //Game State 1
            if (isMainState1){
                //draw boxes game state 1
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++)
                        flowers[i][j].render();
                }
                //draw ticks game state 1
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        if (ticks[i][j].status){
                            ticks[i][j].render();
                        }
                    }
                }
                //update betting game state 1
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    updateBetting(ticks);
                }
            }

            //Switch games state 2 to 3
            for (int i = 0; i < 4; i++){
                if (worms[i].getPoint() > 39){
                    isMainState2 = false;
                    isMainState3 = true;
                    if (!isMoveBox2){
                        bonusMove = "";
                        for (int j = 0; j < 4; j ++){
                            if (ticks[i][j].status){
                                worms[j].move(3);
                                worms[j].bonus = true;
                            }
                        }
                        isMoveBox2 = true;
                    }
                    for (int k=0; k<4; k++){
                        Wurm.batch.draw(sewage1, 840 + 20, 150*k);
                    }
                }
            }

            // Game State 2
            if (isMainState2){
                //draw boxes game state 2
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++)
                        strawberries[i][j].render();
                }
                //draw ticks game state 2
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        if (ticks[i][j].status){
                            ticks[i][j].render();
                        }
                    }
                }
                //update betting game state 2
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    updateBetting(ticks);
                }
            }

            //Game State 3
            if (isMainState3){
                for (int i = 0; i < 4; i ++){
                    if (worms[i].getPoint() >= 55){
                        Wurm.batch.draw(notiBox, 365, 630);
                        String winnerText = worms[i].id + " win!";
                        layout.setText(notiFont, winnerText);
                        textX = 365 + (notiBox.getWidth() - layout.width) / 2;
                        textY = 630 + (notiBox.getHeight() + layout.height) / 2;
                        notiFont.draw(Wurm.batch,winnerText,textX,textY);

                        timeEndState += delta;
                        //switch to end state
                        if (timeEndState >= 3)
                            gsm.push(new EndState(game, gsm));
                    }
                }
            }

            //draw worm's turn
            currentPlayer = worms[dice.getTurn()].id +" turn!";
            turnFont.draw(Wurm.batch, currentPlayer, 30, 680);

            //display bonus for worms in StateGame 2
            if (isMainState2){
                if (countBonus1 <= 7){
                    for (int i=0; i<4; i++){
                        if (worms[i].bonus) {
                            nameFont.draw(Wurm.batch, "+ 3 moves!", 160, 70 + 150 * i);
                        }
                    }
                    countBonus1 += delta;
                }
                else if (countBonus1 > 7 && countBonus1 < 8) {
                    for (int i = 0; i < 4; i++){
                        worms[i].bonus = false;
                    }
                }
            }

            //display bonus for worms in GameState 3
            if (isMainState3)
            {
                if (countBonus2 <= 7){
                    for (int i=0; i<4; i++){
                        if (worms[i].bonus)
                            nameFont.draw(Wurm.batch, "+ 3 moves!", 160, 70 + 150 * i);
                    }
                    countBonus2 += delta;
                }
            }
        //box end
        Wurm.batch.end();
    }
    /**
     * Update the betting box
     * @param tick
     */
    public void updateBetting(Tick tick[][]){
        float mousex = Gdx.input.getX();
        float mousey = Gdx.graphics.getHeight() - Gdx.input.getY();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (ticks[i][j].check(mousex,mousey)){
                    if (ticks[i][j].status){
                        ticks[i][j].status = false;
                    } else {
                        for (int k = 0; k < 4; k ++){
                            ticks[k][j].status = false;
                        }
                        ticks[i][j].status = true;
                    }
                }
            }
        }
    }

    /**
     * Resizes the game viewport when the window size changes.
     *
     * @param width The new window width.
     * @param height The new window height.
     */

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
