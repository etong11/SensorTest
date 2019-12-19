/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Ultrasonic;
//import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * <p>The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 *
 * <p>WARNING: While it may look like a good choice to use for your code if
 * you're inexperienced, don't. Unless you know what you are doing, complex code
 * will be much more difficult under this system. Use TimedRobot or
 * Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
 /*
  private final DifferentialDrive m_robotDrive
      = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
  private final Joystick m_stick = new Joystick(0);
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Ultrasonic ultra;
  //private AnalogInput exampleAnalog;
  //private int bits;
*/
  private ColorSensor color_sensor;

  private static final int ULTRASONIC_ECHO_PULSE_OUTPUT=1;
  private static final int ULTRASONIC_TRIGGER_PULSE_INPUT=1;

  public Robot() {
    /*m_robotDrive.setExpiration(0.1);
    ultra = new Ultrasonic(ULTRASONIC_ECHO_PULSE_OUTPUT, ULTRASONIC_TRIGGER_PULSE_INPUT); // creates the ultra object andassigns ultra to be an ultrasonic sensor which uses DigitalOutput 1 for 
        // the echo pulse and DigitalInput 1 for the trigger pulse
    //exampleAnalog = new AnalogInput(0);
    */
    color_sensor=new ColorSensor(0);
  }

  public boolean detectRed(){
    if (color_sensor.red()>=150)
      return true;
    else
      return false;
  }

  public boolean detectBlue(){
    if (color_sensor.blue()>=110)
      return true;
    else
      return false;
  }

  public boolean detectTape(){
    if (color_sensor.alpha()<=0.3)
      return true;
    else
      return false;
  }
  
  //Analog
  /* public void analogOversampleAverage(){
    exampleAnalog.setOversampleBits(4);
    bits = exampleAnalog.getOversampleBits();
    exampleAnalog.setAverageBits(2);
    bits = exampleAnalog.getAverageBits();
  }
  */

  public void ultrasonicSample() {
    double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor
  }

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto modes", m_chooser);
    //ultra.setAutomaticMode(true); // turns on automatic mode

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the if-else structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   *
   * <p>If you wanted to run a similar autonomous mode with an TimedRobot
   * you would write:
   *
   * <blockquote><pre>{@code
   * Timer timer = new Timer();
   *
   * // This function is run once each time the robot enters autonomous mode
   * public void autonomousInit() {
   *     timer.reset();
   *     timer.start();
   * }
   *
   * // This function is called periodically during autonomous
   * public void autonomousPeriodic() {
   * // Drive for 2 seconds
   *     if (timer.get() < 2.0) {
   *         myRobot.drive(-0.5, 0.0); // drive forwards half speed
   *     } else if (timer.get() < 5.0) {
   *         myRobot.drive(-1.0, 0.0); // drive forwards full speed
   *     } else {
   *         myRobot.drive(0.0, 0.0); // stop robot
   *     }
   * }
   * }</pre></blockquote>
   */
  @Override
  public void autonomous() {
    String autoSelected = m_chooser.getSelected();
    // String autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + autoSelected);

    // MotorSafety improves safety when motors are updated in loops
    // but is disabled here because motor updates are not looped in
    // this autonomous mode.
    m_robotDrive.setSafetyEnabled(false);

    switch (autoSelected) {
      case kCustomAuto:
        // Spin at half speed for two seconds
        m_robotDrive.arcadeDrive(0.0, 0.5);
        Timer.delay(2.0);

        // Stop robot
        m_robotDrive.arcadeDrive(0.0, 0.0);
        break;
      case kDefaultAuto:
      default:
        // Drive forwards for two seconds
        m_robotDrive.arcadeDrive(-0.5, 0.0);
        Timer.delay(2.0);

        // Stop robot
        m_robotDrive.arcadeDrive(0.0, 0.0);
        break;
    }
  }

  /**
   * Runs the motors with arcade steering.
   *
   * <p>If you wanted to run a similar teleoperated mode with an TimedRobot
   * you would write:
   *
   * <blockquote><pre>{@code
   * // This function is called periodically during operator control
   * public void teleopPeriodic() {
   *     myRobot.arcadeDrive(stick);
   * }
   * }</pre></blockquote>
   */
  @Override
  public void operatorControl() {
    m_robotDrive.setSafetyEnabled(true);
    while (isOperatorControl() && isEnabled()) {
      // Drive arcade style
      m_robotDrive.arcadeDrive(-m_stick.getY(), m_stick.getX());

      // The motors will be updated every 5ms
      Timer.delay(0.005);
    }
  }

  /**
   * Runs during test mode.
   */
  @Override
  public void test() {
    /*
    //Moves/repositions the robot then puts a hatch on
    if (!detectTape()){
      m_Drivetrain.drive(0,0.2); //time this
    }
    else {
      m_Drivetrain.drive(0);
      m_hatch.extendHatch();
    } */
    System.out.println(color_sensor.red());
  }
}
