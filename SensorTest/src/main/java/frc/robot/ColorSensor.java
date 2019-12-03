/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.I2C;
import java.nio.ByteBuffer;

//import java.nio.ByteOrder;

/**
 * https://docs.broadcom.com/docs/APDS-9151-DS
 */
public class ColorSensor {
    /*
    private final static int MAIN_CTRL=0x00;
    private final static int PART_ID=0x06;
    private final static int MAIN_STATUS=0x07;
    private final static int INT_CFG=0x19;
    private final static int INT_PST=0x1A;

    /* Uses PS
    private final static int PS_LED=0x01;
    private final static int PS_PULSES=0x02;
    private final static int PS_MEAS_RATE=0x03;
    */
    //Will use LS, doesn't use LSB/MSB (uses intervening data byte)
    //Have to check which ones are needed
    /*
    private final static int LS_MEAS_RATE=0x04;
    private final static int LS_GAIN=0x05;
    private final static int LS_DATA_IR_1=0x0B;
    private final static int LS_DATA_GREEN_1=0x0E;
    private final static int LS_DATA_BLUE_1=0x11;
    private final static int LS_DATA_RED_1=0x14;
    private final static int LS_THRES_UP_1=0x22;
    private final static int LS_THRES_LOW_1=0x25;
    private final static int LS_THRES_VAR=0x27;
    */

    //L-low H-high
    private final static int ENABLE_REGISTER=0x00;
    private final static int ATIME_REGISTER=0x01;
    private final static int WTIME_REGISTER=0x03;
    private final static int AILTL_REGISTER=0x04;
    private final static int AILTH_REGISTER=0x05;
    private final static int AIHTL_REGISTER=0x06;
    private final static int AIHTH_REGISTER=0x07;
    private final static int PILTL_REGISTER=0x08;
    private final static int PILTH_REGISTER=0x09;
    private final static int PIHTL_REGISTER=0x0A;
    private final static int PIHTH_REGISTER=0x0B;
    private final static int PERS_REGISTER=0x0C;
    private final static int CONFIG_REGISTER=0x0D;
    private final static int PPULSE_REGISTER=0x0E;
    private final static int CONTROL_REGISTER=0x0F;
    private final static int REVISION_REGISTER=0x11;
    private final static int ID_REGISTER=0x12;
    private final static int STATUS_REGISTER=0x13;
    private final static int CDATA_REGISTER=0x14;
    private final static int CDATAH_REGISTER=0x15;
    private final static int RDATA_REGISTER=0x16;
    private final static int RDATAH_REGISTER=0x17;
    private final static int GDATA_REGISTER=0x18;
    private final static int GDATAH_REGISTER=0x19;
    private final static int BDATA_REGISTER=0x1A;
    private final static int BDATAH_REGISTER=0x1B;
    private final static int PDATA_REGISTER=0x1C;
    private final static int PDATAH_REGISTER=0x1D;

    private final static int PON   = 0b00000001;
    private final static int AEN   = 0b00000010;
    private final static int PEN   = 0b00000100;
    private final static int WEN   = 0b00001000;
    private final static int AIEN  = 0b00010000;
    private final static int PIEN  = 0b00100000;

    private ByteBuffer buffer;
    private I2C sensor;    

    public ColorSensor(I2C.Port port) {
        sensor = new I2C(port, 0x39); //port, I2C address    

        sensor.write(0x00, 0b00000011); //power on, color sensor on
        buffer=ByteBuffer.allocate(5);
    }

/*protected int readWordRegister(int address) {
    ByteBuffer buf = ByteBuffer.allocate(1);
    sensor.read(COMMAND_REGISTER_BIT | MULTI_BYTE_BIT | address, 2, buf);
    buf.order(ByteOrder.LITTLE_ENDIAN);
    return buf.getShort(0);
    }
    */

public int red() {
    sensor.read(RDATA_REGISTER,2,buffer);
    return buffer.get(0);
}

public int green() {
    sensor.read(GDATA_REGISTER,2,buffer);
    return buffer.get(0);
}

public int blue() {
    sensor.read(BDATA_REGISTER,2,buffer);
    return buffer.get(0);
}

public int clear() {
    sensor.read(CDATA_REGISTER,2,buffer);
    return buffer.get(0);}

public int proximity() {
    sensor.read(PDATA_REGISTER,2,buffer);
    return buffer.get(0);}
}

/*    protected final static int CMD = 0x80;
protected final static int MULTI_BYTE_BIT = 0x20;
protected final static int ENABLE_REGISTER  = 0x00;
protected final static int ATIME_REGISTER   = 0x01;
protected final static int PPULSE_REGISTER  = 0x0E;

protected final static int ID_REGISTER     = 0x12;
protected final static int CDATA_REGISTER  = 0x14;
protected final static int RDATA_REGISTER  = 0x16;
protected final static int GDATA_REGISTER  = 0x18;
protected final static int BDATA_REGISTER  = 0x1A;
protected final static int PDATA_REGISTER  = 0x1C;

protected final static int PON   = 0b00000001;
protected final static int AEN   = 0b00000010;
protected final static int PEN   = 0b00000100;
protected final static int WEN   = 0b00001000;
protected final static int AIEN  = 0b00010000;
protected final static int PIEN  = 0b00100000;

private final double integrationTime = 10;


private I2C sensor;

private ByteBuffer buffy = ByteBuffer.allocate(8);

public short red = 0, green = 0, blue = 0, prox = 0;

public ColorSensor(I2C.Port port) {
	buffy.order(ByteOrder.LITTLE_ENDIAN);
    sensor = new I2C(port, 0x39); //0x39 is the address of the Vex ColorSensor V2
    
    sensor.write(CMD | 0x00, PON | AEN | PEN);
    
    sensor.write(CMD | 0x01, (int) (256-integrationTime/2.38)); //configures the integration time (time for updating color data)
    sensor.write(CMD | 0x0E, 0b1111);
    
}

public void read() {
	buffy.clear();
    sensor.read(CMD | MULTI_BYTE_BIT | RDATA_REGISTER, 8, buffy);
    
    red = buffy.getShort(0);
    if(red < 0) { red += 0b10000000000000000; }
    
    green = buffy.getShort(2);
    if(green < 0) { green += 0b10000000000000000; }
    
    blue = buffy.getShort(4); 
    if(blue < 0) { blue += 0b10000000000000000; }
    
    prox = buffy.getShort(6); 
    if(prox < 0) { prox += 0b10000000000000000; }
    
}

public int status() {
	buffy.clear();
	sensor.read(CMD | 0x13, 1, buffy);
	return buffy.get(0);
}

public void free() {
	//sensor.free();
}
}
*/

/* package frc.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import java.nio.ByteBuffer;

public class ColorSensor {
    protected final static int COMMAND_REGISTER_BIT = 0x80;
    I2C sensor;
    public ColorSensor() {
        sensor = new I2C(I2C.Port.kOnboard, 0x39);
        sensor.write(COMMAND_REGISTER_BIT | 0x00, 0b00000011);
    }

    public void update(){
        System.out.println(sensor.read(0x60, 7, ByteBuffer.allocate(7)));
    }
} */