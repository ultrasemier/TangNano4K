package Gowin

import spinal.core
import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba3.ahblite._
import spinal.lib.bus.amba3.apb._
import spinal.lib.io.{TriState, TriStateArray}

case class Gowin_EMPU_I2c() extends Bundle{
  val scl = inout(Analog(Bool()))
  val sda = inout(Analog(Bool()))
}

case class Gowin_EMPU_Spi() extends Bundle{
  val mosi = out(Bool())
  val miso = in(Bool())
  val sclk = out(Bool())
  val nss = out(Bool())
}

case class Gowin_EMPU_Uart() extends Bundle {
  val rxd = in(Bool())
  val txd = out(Bool())
}

case class Gowin_EMPU_Apb_Com() extends Bundle {
  val pclk    = out(Bool())
  val rst     = out(Bool())
  val penable = out(Bool())
  val paddr   = out(Bits(8 bit))
  val pwrite  = out(Bool())
  val pwdata  = out(Bits(32 bit))
  val pstrb   = out(Bits(4 bit))
  val pprot   = out(Bits(3 bit))
}

case class Gowin_EMPU_Apb() extends Bundle {
  val psel    = out(Bool())
  val prdata  = in(Bits(32 bit))
  val pready  = in(Bool())
  val pslverr = in(Bool())
}

case class Gowin_EMPU_Ahb_Master() extends Bundle {
  val hclk      = out(Bool())
  val hrst      = out(Bool())
  val hsel      = out(Bool())
  val haddr     = out(Bits(32 bit))
  val htrans    = out(Bits(2 bit))
  val hwrite    = out(Bool())
  val hsize     = out(Bits(3 bit))
  val hburst    = out(Bits(3 bit))
  val hprot     = out(Bits(4 bit))
  val memattr   = out(Bits(2 bit))
  val exreq     = out(Bool())
  val hmaster   = out(Bits(4 bit))
  val hwdata    = out Bits (32 bit)
  val hmastlock = out(Bool())
  val hreadymux = out(Bool())
  val hauser    = out(Bool())
  val hwuser    = out(Bits(4 bit))
  val hrdata    = in(Bits(32 bit))
  val hreadyout = in(Bool())
  val hresp     = in(Bool())
  val exresp    = in(Bool())
  val hruser    = in(Bits(3 bit))
}

case class Gowin_EMPU_Ahb_Slave() extends Bundle {
  val hclk      = in(Bool())
  val hrst      = in(Bool())
  val hsel      = in(Bool())
  val haddr     = in(Bits(32 bit))
  val htrans    = in(Bits(2 bit))
  val hwrite    = in(Bool())
  val hsize     = in(Bits(3 bit))
  val hburst    = in(Bits(3 bit))
  val hprot     = in(Bits(4 bit))
  val memattr   = in(Bits(2 bit))
  val exreq     = in(Bool())
  val hmaster   = in(Bits(4 bit))
  val hwdata    = in Bits (32 bit)
  val hmastlock = in(Bool())
  val hreadymux = in(Bool())
  val hauser    = in(Bool())
  val hwuser    = in(Bits(4 bit))
  val hrdata    = out(Bits(32 bit))
  val hreadyout = out(Bool())
  val hresp     = out(Bool())
  val exresp    = out(Bool())
  val hruser    = out(Bits(3 bit))
}

case class Gowin_EMPU_User_Int() extends Bundle {
  val int_0 = in(Bool())
  val int_1 = in(Bool())
  val int_2 = in(Bool())
  val int_3 = in(Bool())
  val int_4 = in(Bool())
  val int_5 = in(Bool())
}

case class Gowin_EMPU_Tpiu() extends Bundle{
  val trace_clk = out(Bool())
  val trace_data = out(Bool())
}

case class Gowin_EMPU_Top_Config() {
  var rtc_en    = false //true
  var i2c_en    = false //true
  var spi_en    = false //true
  var gpio_en   = false //true
  var gpio_tri_en = false //true
  var uart0_en  = false //true
  var uart1_en  = false //true
  var apb1_en   = false //true
  var apb2_en   = false //true
  var apb3_en   = false //true
  var apb4_en   = false //true
  var apb5_en   = false //true
  var apb6_en   = false //true
  var apb7_en   = false //true
  var apb8_en   = false //true
  var apb9_en   = false //true
  var apb10_en  = false //true
  var apb11_en  = false //true
  var apb12_en  = false //true
  var ahb_master_en = false //true
  var ahb_slave_en  = false //true
  var user_int_0_en = false //true
  var user_int_1_en = false //true
  var user_int_2_en = false //true
  var user_int_3_en = false //true
  var user_int_4_en = false //true
  var user_int_5_en = false //true
  var tpiu_en       = false //true

  val apb_com_en = apb1_en | apb2_en | apb3_en | apb4_en | apb5_en |
    apb6_en | apb7_en | apb8_en | apb9_en | apb10_en | apb11_en | apb12_en
  if(gpio_tri_en) gpio_en = false
}

case class Gowin_EMPU_Top(config: Gowin_EMPU_Top_Config) extends BlackBox {
  val io = new Bundle {
    val sys_clk = in(Bool())
    val reset_n = in(Bool())
    val rtc_clk = config.rtc_en     generate in(Bool())
    val i2c     = config.i2c_en     generate Gowin_EMPU_I2c()
    val spi     = config.spi_en     generate Gowin_EMPU_Spi()
    val gpio    = config.gpio_en    generate inout(Analog(Bits(16 bit)))
    val gpioTri = config.gpio_tri_en    generate master(TriStateArray(16 bit))
    val uart0   = config.uart0_en   generate Gowin_EMPU_Uart()
    val uart1   = config.uart1_en   generate Gowin_EMPU_Uart()
    val apb_com = config.apb_com_en generate Gowin_EMPU_Apb_Com()
    val apb1    = config.apb1_en    generate Gowin_EMPU_Apb()
    val apb2    = config.apb2_en    generate Gowin_EMPU_Apb()
    val apb3    = config.apb3_en    generate Gowin_EMPU_Apb()
    val apb4    = config.apb4_en    generate Gowin_EMPU_Apb()
    val apb5    = config.apb5_en    generate Gowin_EMPU_Apb()
    val apb6    = config.apb6_en    generate Gowin_EMPU_Apb()
    val apb7    = config.apb7_en    generate Gowin_EMPU_Apb()
    val apb8    = config.apb8_en    generate Gowin_EMPU_Apb()
    val apb9    = config.apb9_en    generate Gowin_EMPU_Apb()
    val apb10   = config.apb10_en   generate Gowin_EMPU_Apb()
    val apb11   = config.apb11_en   generate Gowin_EMPU_Apb()
    val apb12   = config.apb12_en   generate Gowin_EMPU_Apb()
    val ahbm    = config.ahb_master_en    generate Gowin_EMPU_Ahb_Master()
    val ahbs    = config.ahb_slave_en     generate Gowin_EMPU_Ahb_Slave()
    val user_int_0 = config.user_int_0_en generate in(Bool())
    val user_int_1 = config.user_int_1_en generate in(Bool())
    val user_int_2 = config.user_int_2_en generate in(Bool())
    val user_int_3 = config.user_int_3_en generate in(Bool())
    val user_int_4 = config.user_int_4_en generate in(Bool())
    val user_int_5 = config.user_int_5_en generate in(Bool())
    val tpiu       = config.tpiu_en       generate Gowin_EMPU_Tpiu()
  }

  mapCurrentClockDomain(io.sys_clk, io.reset_n)
  noIoPrefix()
  //addRTLPath("./xxx.v")
  private def renameIO(): Unit = {
    io.flatten.foreach(bt => {
      if (bt.getName().contains("i2c")) bt.setName(bt.getName().replace("i2c_", ""))
      if (bt.getName().contains("gpioTri_read")) bt.setName(bt.getName().replace("gpioTri_read","gpioin"))
      if (bt.getName().contains("gpioTri_writeEnable")) bt.setName(bt.getName().replace("gpioTri_writeEnable","gpioouten"))
      if (bt.getName().contains("gpioTri_write")) bt.setName(bt.getName().replace("gpioTri_write","gpioout"))
      if (bt.getName().contains("apb_com")) bt.setName(bt.getName().replace("apb_com_", "master_"))
      if (bt.getName().contains("apb1")) bt.setName(bt.getName().replace("apb1_", "master_") + "1")
      if (bt.getName().contains("apb2")) bt.setName(bt.getName().replace("apb2_", "master_") + "2")
      if (bt.getName().contains("apb3")) bt.setName(bt.getName().replace("apb3_", "master_") + "3")
      if (bt.getName().contains("apb4")) bt.setName(bt.getName().replace("apb4_", "master_") + "4")
      if (bt.getName().contains("apb5")) bt.setName(bt.getName().replace("apb5_", "master_") + "5")
      if (bt.getName().contains("apb6")) bt.setName(bt.getName().replace("apb6_", "master_") + "6")
      if (bt.getName().contains("apb7")) bt.setName(bt.getName().replace("apb7_", "master_") + "7")
      if (bt.getName().contains("apb8")) bt.setName(bt.getName().replace("apb8_", "master_") + "8")
      if (bt.getName().contains("apb9")) bt.setName(bt.getName().replace("apb9_", "master_") + "9")
      if (bt.getName().contains("apb10")) bt.setName(bt.getName().replace("apb10_", "master_") + "10")
      if (bt.getName().contains("apb11")) bt.setName(bt.getName().replace("apb11_", "master_") + "11")
      if (bt.getName().contains("apb12")) bt.setName(bt.getName().replace("apb12_", "master_") + "12")
      if (bt.getName().contains("ahbm")) bt.setName(bt.getName().replace("ahbm_", "master_"))
      if (bt.getName().contains("ahbs")) bt.setName(bt.getName().replace("ahbs_", "slave_"))
    })
  }
  addPrePopTask(() => renameIO())
}

case class Gowin_EMPU_Wapper(config: Gowin_EMPU_Top_Config, apbConfig: Apb3Config, ahbConfig: AhbLite3Config) extends Component {
  val io = new Bundle {
    //val sys_clk = in(Bool())
    //val reset_n = in(Bool())
    val rtc_clk = config.rtc_en     generate in(Bool())
    val i2c     = config.i2c_en     generate Gowin_EMPU_I2c()
    val spi     = config.spi_en     generate Gowin_EMPU_Spi()
    val gpio    = config.gpio_en    generate inout(Analog(Bits(16 bit)))
    val gpioTri = config.gpio_tri_en    generate master(TriStateArray(16 bits))
    val uart0   = config.uart0_en   generate Gowin_EMPU_Uart()
    val uart1   = config.uart1_en   generate Gowin_EMPU_Uart()
    val apb_com = config.apb_com_en generate Gowin_EMPU_Apb_Com()
    val apb1    = config.apb1_en    generate Apb3(apbConfig).asMaster()
    val apb2    = config.apb2_en    generate Apb3(apbConfig).asMaster()
    val apb3    = config.apb3_en    generate Apb3(apbConfig).asMaster()
    val apb4    = config.apb4_en    generate Apb3(apbConfig).asMaster()
    val apb5    = config.apb5_en    generate Apb3(apbConfig).asMaster()
    val apb6    = config.apb6_en    generate Apb3(apbConfig).asMaster()
    val apb7    = config.apb7_en    generate Apb3(apbConfig).asMaster()
    val apb8    = config.apb8_en    generate Apb3(apbConfig).asMaster()
    val apb9    = config.apb9_en    generate Apb3(apbConfig).asMaster()
    val apb10   = config.apb10_en   generate Apb3(apbConfig).asMaster()
    val apb11   = config.apb11_en   generate Apb3(apbConfig).asMaster()
    val apb12   = config.apb12_en   generate Apb3(apbConfig).asMaster()
    val ahbm    = config.ahb_master_en    generate master(AhbLite3(ahbConfig))
    val ahbmClk = config.ahb_master_en    generate out(Bool())
    val ahbmRst = config.ahb_master_en    generate out(Bool())
    val ahbs    = config.ahb_slave_en     generate slave(AhbLite3(ahbConfig))
    val user_int_0 = config.user_int_0_en generate in(Bool())
    val user_int_1 = config.user_int_1_en generate in(Bool())
    val user_int_2 = config.user_int_2_en generate in(Bool())
    val user_int_3 = config.user_int_3_en generate in(Bool())
    val user_int_4 = config.user_int_4_en generate in(Bool())
    val user_int_5 = config.user_int_5_en generate in(Bool())
    val tpiu       = config.tpiu_en       generate Gowin_EMPU_Tpiu()
  }
  val empu = Gowin_EMPU_Top(config)
  //empu.io.reset_n <> io.reset_n
  //empu.io.sys_clk <> io.sys_clk
  config.ahb_master_en generate {
    empu.io.ahbm.hclk <> io.ahbmClk
    empu.io.ahbm.hrst <> io.ahbmRst
    empu.io.ahbm.hsel <> io.ahbm.HSEL
    empu.io.ahbm.haddr.asUInt <> io.ahbm.HADDR
    empu.io.ahbm.htrans <> io.ahbm.HTRANS
    empu.io.ahbm.hwrite <> io.ahbm.HWRITE
    empu.io.ahbm.hsize <> io.ahbm.HSIZE
    empu.io.ahbm.hburst <> io.ahbm.HBURST
    empu.io.ahbm.hprot <> io.ahbm.HPROT
    //empu.io.ahbm.memattr
    //empu.io.ahbm.exreq
    //empu.io.ahbm.hmaster
    empu.io.ahbm.hwdata <> io.ahbm.HWDATA
    empu.io.ahbm.hmastlock <> io.ahbm.HMASTLOCK
    //empu.io.ahbm.hreadymux
    //empu.io.ahbm.hauser
    //empu.io.ahbm.hwuser
    empu.io.ahbm.hrdata <> io.ahbm.HRDATA
    empu.io.ahbm.hreadyout <> io.ahbm.HREADYOUT
    empu.io.ahbm.hresp <> io.ahbm.HRESP
    empu.io.ahbm.exresp <> False //in
    empu.io.ahbm.hruser <> B(0x0) //in
    io.ahbm.HREADY <> True
  }

  config.gpio_en generate {
    empu.io.gpio <> io.gpio
  }
  config.gpio_tri_en generate {
    empu.io.gpioTri <> io.gpioTri
  }
  config.uart0_en generate {
    empu.io.uart0.txd <> io.uart0.txd
    empu.io.uart0.rxd <> io.uart0.rxd
  }
  config.uart1_en generate {
    empu.io.uart1.txd <> io.uart1.txd
    empu.io.uart1.rxd <> io.uart1.rxd
  }
  config.i2c_en generate {
    empu.io.i2c.scl <> io.i2c.scl
    empu.io.i2c.sda <> io.i2c.sda
  }
}


//case class Gowin_EMPU_Top_Test() extends Component {
//  val io = new Bundle {
//    val x = out(Bool())
//    val gpio = inout(Analog(Bits(16 bits)))
//  }
//  val config = Gowin_EMPU_Top_Config()
//  val emu = Gowin_EMPU_Top(config)
//
//  emu.io.sys_clk := False
//  emu.io.reset_n := False
//  //emu.io.gpio := B(0xffff).resized
//  emu.io.uart0.rxd := True
//  emu.io.uart1.rxd := True
//  emu.io.apb1.prdata := B(0x0).resized
//  emu.io.apb1.pready := True
//  emu.io.apb1.pslverr := False
//  emu.io.apb2.prdata := B(0x0).resized
//  emu.io.apb2.pready := True
//  emu.io.apb2.pslverr := False
//  emu.io.apb3.prdata := B(0x0).resized
//  emu.io.apb3.pready := True
//  emu.io.apb3.pslverr := False
//  emu.io.apb4.prdata := B(0x0).resized
//  emu.io.apb4.pready := True
//  emu.io.apb4.pslverr := False
//  emu.io.apb5.prdata := B(0x0).resized
//  emu.io.apb5.pready := True
//  emu.io.apb5.pslverr := False
//  emu.io.apb6.prdata := B(0x0).resized
//  emu.io.apb6.pready := True
//  emu.io.apb6.pslverr := False
//  emu.io.apb7.prdata := B(0x0).resized
//  emu.io.apb7.pready := True
//  emu.io.apb7.pslverr := False
//  emu.io.apb8.prdata := B(0x0).resized
//  emu.io.apb8.pready := True
//  emu.io.apb8.pslverr := False
//  emu.io.apb9.prdata := B(0x0).resized
//  emu.io.apb9.pready := True
//  emu.io.apb9.pslverr := False
//  emu.io.apb10.prdata := B(0x0).resized
//  emu.io.apb10.pready := True
//  emu.io.apb10.pslverr := False
//  emu.io.apb11.prdata := B(0x0).resized
//  emu.io.apb11.pready := True
//  emu.io.apb11.pslverr := False
//  emu.io.apb12.prdata := B(0x0).resized
//  emu.io.apb12.pready := True
//  emu.io.apb12.pslverr := False
//  emu.io.ahbm.hrdata := B(0x0).resized
//  emu.io.ahbm.hreadyout := False
//  emu.io.ahbm.hresp := False
//  emu.io.ahbm.exresp := False
//  emu.io.ahbm.hruser := B(0x0).resized
//  emu.io.user_int_0 := False
//  emu.io.user_int_1 := False
//  emu.io.user_int_2 := False
//  emu.io.user_int_3 := False
//  emu.io.user_int_4 := False
//  emu.io.user_int_5 := False
//
//  io.x := emu.io.uart0.txd
//  io.gpio <> emu.io.gpio
//}
//

object Gowin_EMPU_Top_Test {
  def main(args: Array[String]) {
    val config = SpinalConfig(targetDirectory = "./rtl")
    config.generateVerilog({
      val top = Gowin_EMPU_Top(Gowin_EMPU_Top_Config())
      top
    })
  }
}

