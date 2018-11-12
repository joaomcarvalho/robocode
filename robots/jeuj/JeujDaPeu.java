package jeuj;
import robocode.*;
import java.awt.Color;


public class JeujDaPeu extends AdvancedRobot {

public void run() {
    setAdjustGunForRobotTurn(true);
    setColors(Color.red,Color.blue,Color.green);

    while (true) {
        turnGunRight(739.0349845819807);
        setAhead(454.68620205297907);
    }
}
public void onScannedRobot(ScannedRobotEvent e) {
    setAhead(96.1686416227715);

    setTurnRight(63.9627115241042);

    setTurnGunRight(348.8466079683611);

    setTurnRadarRight(323.1847254446084);

    if (e.getDistance() < 14.485980821508662) {
        setFire(8.819933238090393);
    } else {
        setFire(9.113476597801922);
    }
}
public void onHitByBullet(HitByBulletEvent e) {
    setTurnRight(497.3473266324282);
    setAhead(55.61361386700132 * -1);
}
public void onHitWall(HitWallEvent e) {
    back(94.09096525345424);
    setAhead(574.5771139817991 * -1);
}
}