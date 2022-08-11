//Copyright (C)2014-2022 GOWIN Semiconductor Corporation.
//All rights reserved.
//File Title: Timing Constraints file
//GOWIN Version: 1.9.8.03 
//Created Time: 2022-08-08 19:06:04
create_clock -name io_sys_clk -period 10 -waveform {0 5} [get_nets {io_sys_clk}]
