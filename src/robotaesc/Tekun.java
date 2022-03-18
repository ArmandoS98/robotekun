/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package robotaesc;

import java.awt.Color;
import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

/**
 *
 * @author arman
 */
public class Tekun extends AdvancedRobot {

    private int moveDirection = 1;

    @Override
    public void run() {
        setAdjustRadarForRobotTurn(true);
        setBodyColor(new Color(0, 0, 255)); //tank background color
        setGunColor(new Color(50, 50, 20));
        setRadarColor(new Color(200, 200, 70));
        setScanColor(Color.white);
        setBulletColor(Color.blue); //Bullet color
        setAdjustGunForRobotTurn(true);
        turnRadarRightRadians(Double.POSITIVE_INFINITY);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double absBearing = e.getBearingRadians() + getHeadingRadians();
        double latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing);
        double gunTurnAmt;
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians());
        if (Math.random() > .9) {
            setMaxVelocity((12 * Math.random()) + 12);
        }
        if (e.getDistance() > 150) {
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 22);
            setTurnGunRightRadians(gunTurnAmt);
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing - getHeadingRadians() + latVel / getVelocity()));
            setAhead((e.getDistance() - 140) * moveDirection);
            setFire(3);
        } else {
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 15);
            setTurnGunRightRadians(gunTurnAmt);
            setTurnLeft(-90 - e.getBearing());
            setAhead((e.getDistance() - 140) * moveDirection);
            setFire(3);
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        moveDirection = -moveDirection;
    }

    @Override
    public void onWin(WinEvent event) {
        for (int i = 0; i < 50; i++) {
            turnRight(30);
            turnLeft(30);
        }
    }
}