import nano4k.Nano4k
import spinal.core.sim._

object Nano4kSim {
  def main(args: Array[String]): Unit = {
    SimConfig.withWave.compile {
      val dut = Nano4k()
      dut
    }.doSim { dut =>
      SimTimeout(27000)
      dut.io.sys_rstn #= false
      sleep(101)
      dut.io.sys_rstn #= true
      while (true) {
        dut.io.sys_clk #= false
        sleep(1)
        dut.io.sys_clk #= true
        sleep(1)
      }
    }
  }
}
