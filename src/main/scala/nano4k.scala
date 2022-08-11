package nano4k

import spinal.core._
import spinal.lib.Counter
import spinal.lib.DoCmd.doCmd

case class Nano4k() extends Component {
  val io = new Bundle {
    val sys_clk = in(Bool())
    val sys_rstn = in(Bool())
    val led = new Bundle {
      val r, g, b = out(Bool())
    }
  }

  val clkDomain = new ClockDomain(io.sys_clk, ~io.sys_rstn)
  val domainArea = new ClockingArea(clkDomain) {
    val r = Reg(False)
    val cnt = Counter(27000000/4)
    cnt.willIncrement := True
    r.toggleWhen(cnt.willOverflow)

    io.led.r := r
    io.led.g := False
    io.led.b := True
  }
}

object genHdlMain {
  def main(args: Array[String]): Unit = {
    val config = SpinalConfig(targetDirectory = "src\\main\\rtl\\")
    config.generateVerilog({
      val top = Nano4k()
      top
    }
    )
  }
}

object synthesizePlaceRouteMain {
  def main(args: Array[String]) {
    val gw_sh = "C:\\Gowin\\Gowin_V1.9.8.03\\IDE\\bin\\gw_sh.exe"
    doCmd(
      s"""
         |$gw_sh Nano4k.tcl
         |""".stripMargin,
      "src\\main\\rtl")
  }
}

object programmerMain {
  def main(args: Array[String]) {
    val gw_prog = "C:\\Gowin\\Gowin_V1.9.8.03\\ProgrammerBL702\\bin\\programmer_cli.exe"
    doCmd(
      s"""
         |$gw_prog --device GW1NSER-4C -r 2 --fsFile C:\\Users\\csliu\\Desktop\\TangNano4k\\src\\main\\rtl\\impl\\pnr\\project.fs
         |""".stripMargin,
      "src\\main\\rtl")
  }
}

object Nano4kMain {
  def main(args: Array[String]) = {
    genHdlMain.main(args)
    synthesizePlaceRouteMain.main(args)
    programmerMain.main(args)
  }
}
