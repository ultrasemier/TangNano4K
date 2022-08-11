package Gowin

import spinal.core._

case class IOBUF_Wapper(o: Bool, io: Bool, i: Bool, oen: Bool) {
  val buf = IOBUF()
  buf.I := i.asBits
  buf.OEN := oen.asBits
  o := buf.O.asBool
  io <> buf.IO.asBool
}

case class TLVDS_IBUF_Wapper(o: Bool, io: Bits, iob: Bits) {
  val buf = TLVDS_IBUF()
  o := buf.O.asBool
  buf.I := io
  buf.IB := iob
}

case class TLVDS_IOBUF_Wapper(o: Bool, io: Bits, iob: Bits,i: Bool, oen: Bool) {
  val buf = TLVDS_IOBUF()
  buf.I := i.asBits
  buf.OEN := oen.asBits
  o := buf.O.asBool
  io <> buf.IO
  iob <> buf.IOB
}

case class ELVDS_IOBUF_Wapper(o: Bool, io: Bits, iob: Bits,i: Bool, oen: Bool) {
  val buf = ELVDS_IOBUF()
  buf.I := i.asBits
  buf.OEN := oen.asBits
  o := buf.O.asBool
  io <> buf.IO
  iob <> buf.IOB
}

