package nano4k

import spinal.core._
import spinal.lib.Counter
import spinal.lib.DoCmd.doCmd
import java.io.File

object GoWinPath{
  val path = "C:\\Gowin\\Gowin_V1.9.8.03\\"
  val gw_sh = path + "IDE\\bin\\gw_sh.exe"
  val programmer = path + "ProgrammerBL702\\bin\\programmer_cli.exe"
}
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
    doCmd(
      s"""
         |${GoWinPath.gw_sh} Nano4k.tcl
         |""".stripMargin,
      "src\\main\\rtl")
  }
}

object programmerMain {
  def main(args: Array[String]) {
    val file = new File("");
    val pwd = file.getAbsolutePath
    println(pwd)
    doCmd(
      s"""
         |${GoWinPath.programmer} --device GW1NSER-4C -r 2 --fsFile $pwd\\src\\main\\rtl\\impl\\pnr\\project.fs
         |""".stripMargin)
  }
}

object nano4kMain {
  def main(args: Array[String]) = {
    genHdlMain.main(args)
    synthesizePlaceRouteMain.main(args)
    programmerMain.main(args)
  }
}
