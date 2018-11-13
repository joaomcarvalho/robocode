package jeuj;
import robocode.*;
import java.awt.Color;


public class JeujDaPeu extends AdvancedRobot {

public void run() {
    setAdjustGunForRobotTurn(true);
    setColors(Color.red,Color.blue,Color.green);

    while (true) {
        turnGunRight(20.30561967752975);
        setAhead(339.8786182192478);
    }
}
public void onScannedRobot(ScannedRobotEvent e) {
    setAhead(46.206349269139245);

    setTurnRight(62.02769513139049);

    setTurnGunRight(370.38290095720924);

    setTurnRadarRight(0.05010490467607376);

    if (e.getDistance() < 60.123847177154005) {
        setFire(13.353009945606784);
    } else {
        setFire(7.887119210483245);
    }
}
public void onHitByBullet(HitByBulletEvent e) {
    setTurnRight(401.09876017910517);
    setAhead(229.75500696032302 * -1);
}
public void onHitWall(HitWallEvent e) {
    back(131.39765066471827);
    setAhead(442.9924210980955 * -1);
}
}