package com.example.learning_gamedev.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.learning_gamedev.R;
import com.example.learning_gamedev.gameStates.GameStates;
import com.example.learning_gamedev.gameStates.PlayingState;
import com.example.learning_gamedev.main.MainActivity;
import static com.example.learning_gamedev.utils.Utils.isInsideCircle;

public class PlayingStateUI {
    private final PlayingState playingState;
    private CustomButton menuButton;
    private CustomButton attackButton;

    // For UI
//    private float xCenter, yCenter, radius;
    private PointF joystickCenterPos, innerJoystickCenterPos, attackButtonCenterPos;
    private float radius;
    private Paint redPaint, bluePaint;
    private boolean insideJoystickTouch;

    // For Multitouch
    private int insideJoystickPointerId;
    private int insideAttackButtonPointerId;

    public PlayingStateUI(PlayingState playingState){
        this.playingState = playingState;
        this.menuButton = new CustomButton(20,20, ButtonImages.MENU);
        this.attackButton = new CustomButton(MainActivity.GAME_WIDTH - MainActivity.GAME_WIDTH/7, MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4, ButtonImages.ATTACK_BUTTON);
//        this.xCenter = MainActivity.GAME_WIDTH/7;
//        this.yCenter = MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4;
        this.attackButtonCenterPos = new PointF(MainActivity.GAME_WIDTH - MainActivity.GAME_WIDTH/7, MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4);
        this.joystickCenterPos = new PointF(MainActivity.GAME_WIDTH/7, MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4);
        this.innerJoystickCenterPos = new PointF(MainActivity.GAME_WIDTH/7, MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4);
        this.radius = 150;
        this.redPaint = new Paint();
        this.redPaint.setColor(Color.RED);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeWidth(5);
        this.bluePaint = new Paint();
        this.bluePaint.setColor(Color.BLUE);
        this.bluePaint.setStyle(Paint.Style.STROKE);
        this.bluePaint.setStrokeWidth(5);
        this.insideJoystickTouch = false;
        this.insideJoystickPointerId = -1;
        this.insideAttackButtonPointerId = -1;
    }
    public void draw(Canvas c){
//        c.drawCircle(this.joystickCenterPos.x, this.joystickCenterPos.y, this.radius, this.bluePaint);
//        c.drawCircle(this.attackButtonCenterPos.x, this.attackButtonCenterPos.y, this.radius, this.redPaint);
//        Bitmap outerCircle = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.outer_joystick);
//        outerCircle = Bitmap.createScaledBitmap(outerCircle, 300, 300, false);
//        c.drawBitmap(outerCircle, this.joystickCenterPos.x-this.radius, this.joystickCenterPos.y-this.radius, null);
//
//        Bitmap innerCircle = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.inner_joystick_resized);
//        innerCircle = Bitmap.createScaledBitmap(innerCircle, 140, 140, false);
//        c.drawBitmap(innerCircle, this.innerJoystickCenterPos.x-70, this.innerJoystickCenterPos.y-70, null);
        // joystick (inner and outer)
        this.drawJoystick(c);
        this.drawHeartLevel(c);

        // attack button
        c.drawBitmap(
                this.attackButton.getButtonImage(),
                this.attackButtonCenterPos.x-this.radius/2,
                this.attackButtonCenterPos.y-this.radius/2,
                null
        );

        // home button
        c.drawBitmap(
                this.menuButton.getButtonImage(),
                this.menuButton.getHitBox().left,
                this.menuButton.getHitBox().top,
                null
        );
    }

    private void drawHeartLevel(Canvas c) {
        switch (this.playingState.getPlayer().getHeartLevel()){
            case HEART_LEVEL_ZERO -> c.drawBitmap(HeartImages.HEART_ZERO.getImage(), 10, 300, null);
            case HEART_LEVEL_ONE -> c.drawBitmap(HeartImages.HEART_ONE.getImage(), 10, 300, null);
            case HEART_LEVEL_TWO -> c.drawBitmap(HeartImages.HEART_TWO.getImage(), 10, 300, null);
            case HEART_LEVEL_THREE -> c.drawBitmap(HeartImages.HEART_THREE.getImage(), 10, 300, null);
            case HEART_LEVEL_FOUR -> c.drawBitmap(HeartImages.HEART_FOUR.getImage(), 10, 300, null);
            default -> c.drawBitmap(HeartImages.HEART_ZERO.getImage(), 10, 300, null);
        }
    }

    private void drawJoystick(Canvas c){
        Bitmap outerCircle = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.outer_joystick);
        outerCircle = Bitmap.createScaledBitmap(outerCircle, 300, 300, false);
        c.drawBitmap(outerCircle, this.joystickCenterPos.x-this.radius, this.joystickCenterPos.y-this.radius, null);

        Bitmap innerCircle = BitmapFactory.decodeResource(MainActivity.getContext().getResources(), R.drawable.inner_joystick_resized);
        innerCircle = Bitmap.createScaledBitmap(innerCircle, 140, 140, false);
        c.drawBitmap(innerCircle, this.innerJoystickCenterPos.x-70, this.innerJoystickCenterPos.y-70, null);
    }
//    private boolean isInsideCircle(PointF eventPos, PointF circlePos){
//        float a = Math.abs(eventPos.x - circlePos.x);
//        float b = Math.abs(eventPos.y - circlePos.y);
//
//        float c = (float)Math.sqrt(Math.pow((double)a, 2) + Math.pow((double)b, 2));
//        return c <= this.radius;
//    }
    private boolean isInsideAttackButton(PointF eventPos){
        return isInsideCircle(eventPos, this.attackButtonCenterPos, this.radius);
    }
    private boolean isInsideJoystick(PointF eventPos){
//        if (this.isInsideCircle(eventPos, this.joystickCenterPos)){
//            this.insideJoystickTouch = true;
//            this.insideJoystickPointerId = pointerId;
//            return true;
//        }
//
//        return false;
        return isInsideCircle(eventPos, this.joystickCenterPos, this.radius);
    }
    public void touchEvents(MotionEvent event){
//        System.out.println("in touchEvent!");
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerId = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex), event.getY(actionIndex));
//        float x = event.getX();
//        float y = event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN:
//                System.out.println("action: " + action);
//                System.out.println("actionIndex: " + actionIndex);
//                System.out.println("pointerId: " + pointerId);

//                float a = Math.abs(x - xCenter);
//                float b = Math.abs(y - yCenter);
//
//                float c = (float)Math.sqrt(Math.pow((double)a, 2) + Math.pow((double)b, 2));
//
                if (isInsideJoystick(eventPos)){
                    this.insideJoystickTouch = true;
                    this.insideJoystickPointerId = pointerId;
                }
                else if (this.isInsideAttackButton(eventPos) && insideAttackButtonPointerId == -1){
                    this.insideAttackButtonPointerId = pointerId;
                    this.playingState.setAttacking(true);
                    this.attackButton.setPushed(true);
                }
                else if (this.menuButton.isInButton(eventPos)){
                    this.menuButton.setPointerId(pointerId);
                    this.menuButton.setPushed(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.insideJoystickTouch){
                    for (int i = 0; i < event.getPointerCount(); i++){
                        if (event.getPointerId(i) == this.insideJoystickPointerId){
                            float x = event.getX(i);
                            float y = event.getY(i);
//                            System.out.println("X: " + x + " Y: " + y);
                            if (isInsideCircle(new PointF(x,y),this.joystickCenterPos, this.radius)){
                                this.innerJoystickCenterPos.x = x;
                                this.innerJoystickCenterPos.y = y;
                            }
//                            float xDiff = event.getX(i) - this.joystickCenterPos.x;
//                            float yDiff = event.getY(i) - this.joystickCenterPos.y;

                            this.playingState.setPlayerMove(true, new PointF(event.getX(i), event.getY(i)));
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP:
//                System.out.println("action: " + action);
//                System.out.println("actionIndex: " + actionIndex);
//                System.out.println("pointerId: " + pointerId);
//                System.out.println("------------------------------------------------");
//                System.out.println(this.insideJoystickPointerId);

                if (pointerId == this.insideJoystickPointerId){
                    this.resetJoystickButton();
                }
                else if (pointerId == this.insideAttackButtonPointerId){
                    this.insideAttackButtonPointerId = -1;
                    this.playingState.setAttacking(false);
                    this.attackButton.setPushed(false);
                }
                else if (this.menuButton.isInButton(eventPos)){
                    this.resetJoystickButton();
//                    this.playingState.getGame().setCurrentGameState(GameStates.MENU);
                    this.playingState.setGameState(GameStates.MENU);
                    this.menuButton.setPushed(false);
                }
                else if (pointerId == this.menuButton.getPointerId())
                    this.menuButton.setPushed(false);
                break;
        }
    }
    private void resetJoystickButton(){
        this.innerJoystickCenterPos.x = MainActivity.GAME_WIDTH/7;
        this.innerJoystickCenterPos.y = MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4;
        this.insideJoystickTouch = false;
        this.playingState.setPlayerMove(false, null);
        this.insideJoystickPointerId = -1;
    }
    public PointF getJoystickCenterPos(){
        return this.joystickCenterPos;
    }
}
