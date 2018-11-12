package jeuj;
import robocode.*;
import java.awt.Color;


public class JeujDaPeu extends AdvancedRobot {

public void run() {
    setAdjustGunForRobotTurn(true);
    setColors(Color.red,Color.blue,Color.green);

    while (true) {
        turnGunRight(15.813076205841048);
        setAhead(461.4493284332109);
    }
}
public void onScannedRobot(ScannedRobotEvent e) {
    setAhead(23.238395127222145);

    setTurnRight(150.42805895273304);

    setTurnGunRight(211.7141966024935);

    setTurnRadarRight(93.7939646998764);

    if (e.getDistance() < 54.6570899011529) {
        setFire(11.564541154334822);
    } else {
        setFire(5.077038296882043);
    }
}
public void onHitByBullet(HitByBulletEvent e) {
    setTurnRight(125.4124599687636);
    setAhead(474.41472694431445 * -1);
}
public void onHitWall(HitWallEvent e) {
    back(100.77856249135313);
    setAhead(239.1342452291164 * -1);
}
}