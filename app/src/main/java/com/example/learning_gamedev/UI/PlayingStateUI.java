package com.example.learning_gamedev.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.learning_gamedev.gameStates.GameStates;
import com.example.learning_gamedev.gameStates.PlayingState;
import com.example.learning_gamedev.main.MainActivity;
import static com.example.learning_gamedev.utils.Utils.isInsideCircle;

public class PlayingStateUI {
    private final PlayingState playingState;
    private CustomButton menuButton;

    // For UI
//    private float xCenter, yCenter, radius;
    private PointF joystickCenterPos, attackButtonCenterPos;
    private float radius;
    private Paint redPaint, bluePaint;
    private boolean insideJoystickTouch;

    // For Multitouch
    private int insideJoystickPointerId;
    private int insideAttackButtonPointerId;

    public PlayingStateUI(PlayingState playingState){
        this.playingState = playingState;
        this.menuButton = new CustomButton(20,20, ButtonImages.MENU);
//        this.xCenter = MainActivity.GAME_WIDTH/7;
//        this.yCenter = MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4;
        this.attackButtonCenterPos = new PointF(MainActivity.GAME_WIDTH - MainActivity.GAME_WIDTH/7, MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4);
        this.joystickCenterPos = new PointF(MainActivity.GAME_WIDTH/7, MainActivity.GAME_HEIGHT - MainActivity.GAME_HEIGHT/4);
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
        c.drawCircle(this.joystickCenterPos.x, this.joystickCenterPos.y, this.radius, this.bluePaint);
        c.drawCircle(this.attackButtonCenterPos.x, this.attackButtonCenterPos.y, this.radius, this.redPaint);
        c.drawBitmap(
                this.menuButton.getButtonImage(),
                this.menuButton.getHitBox().left,
                this.menuButton.getHitBox().top,
                null
        );
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
        this.insideJoystickTouch = false;
        this.playingState.setPlayerMove(false, null);
        this.insideJoystickPointerId = -1;
    }
    public PointF getJoystickCenterPos(){
        return this.joystickCenterPos;
    }
}
