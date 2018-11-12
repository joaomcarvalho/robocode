package robocodeIA;

import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * Creates the robot and compiles it.
 */
public class RobotFactory {

	public static void create(double[] chromo) {
		createRobotFile(chromo);
		compile();
	}

	public static void compile() {
		String fileToCompile = "robots/jeuj/JeujDaPeu.java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, fileToCompile);
	}

	public static void createRobotFile(double[] chromo) {
		try {
			FileWriter fstream = new FileWriter("robots/jeuj/JeujDaPeu.java");
			BufferedWriter out = new BufferedWriter(fstream);

			// Robot source code.
			// @formatter:off
			out.write("package jeuj;" +
			        "\nimport robocode.*;" +
					"\nimport java.awt.Color;\n" +
					"\n" +
					"\npublic class " + "JeujDaPeu" + " extends AdvancedRobot {" +
					"\n" +
					"\npublic void run() {" +
					"\n    setAdjustGunForRobotTurn(true);" +
					"\n    setColors(Color.red,Color.blue,Color.green);" +
					"\n" +
					"\n    while (true) {" +
					"\n        turnGunRight(" + chromo[0] + ");" +
					"\n        setAhead(" + chromo[1] + ");" +
					"\n    }" +
					"\n}" +
					"\npublic void onScannedRobot(ScannedRobotEvent e) {" +
					"\n    setAhead(" + chromo[2] + ");" +
					"\n" +
					"\n    setTurnRight(" + chromo[3] + ");" +
					"\n" +
					"\n    setTurnGunRight(" + chromo[4] + ");" +
					"\n" +
					"\n    setTurnRadarRight(" + chromo[5] + ");" +
					"\n" +
					"\n    if (e.getDistance() < " + chromo[6] + ") {" +
					"\n        setFire(" + chromo[7] + ");" +
					"\n    } else {" +
					"\n        setFire(" + chromo[8] + ");" +
					"\n    }" +
					"\n}" +
					"\npublic void onHitByBullet(HitByBulletEvent e) {" +
					"\n    setTurnRight(" + chromo[9] + ");" +
					"\n    setAhead(" + chromo[10] + " * -1);" +
					"\n}" +
					"\npublic void onHitWall(HitWallEvent e) {" +
					"\n    back(" + chromo[11] + ");" +
					"\n    setAhead(" + chromo[12] + " * -1);" +
					"\n}"
			);
			// @formatter:on

			out.append("\n}");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}