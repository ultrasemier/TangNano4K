// Generator : SpinalHDL v1.7.2    git head : 08fc866bebdc40c471ebe327bface63e34406489
// Component : Nano4k

`timescale 1ns/1ps

module Nano4k (
  input               io_sys_clk,
  input               io_sys_rstn,
  output              io_led_r,
  output              io_led_g,
  output              io_led_b
);

  wire       [22:0]   _zz_domainArea_cnt_valueNext;
  wire       [0:0]    _zz_domainArea_cnt_valueNext_1;
  wire                _zz_1;
  reg                 domainArea_r;
  reg                 domainArea_cnt_willIncrement;
  wire                domainArea_cnt_willClear;
  reg        [22:0]   domainArea_cnt_valueNext;
  reg        [22:0]   domainArea_cnt_value;
  wire                domainArea_cnt_willOverflowIfInc;
  wire                domainArea_cnt_willOverflow;
  function  zz_domainArea_cnt_willIncrement(input dummy);
    begin
      zz_domainArea_cnt_willIncrement = 1'b0;
      zz_domainArea_cnt_willIncrement = 1'b1;
    end
  endfunction
  wire  _zz_2;

  assign _zz_domainArea_cnt_valueNext_1 = domainArea_cnt_willIncrement;
  assign _zz_domainArea_cnt_valueNext = {22'd0, _zz_domainArea_cnt_valueNext_1};
  assign _zz_1 = (! io_sys_rstn);
  assign _zz_2 = zz_domainArea_cnt_willIncrement(1'b0);
  always @(*) domainArea_cnt_willIncrement = _zz_2;
  assign domainArea_cnt_willClear = 1'b0;
  assign domainArea_cnt_willOverflowIfInc = (domainArea_cnt_value == 23'h66ff2f);
  assign domainArea_cnt_willOverflow = (domainArea_cnt_willOverflowIfInc && domainArea_cnt_willIncrement);
  always @(*) begin
    if(domainArea_cnt_willOverflow) begin
      domainArea_cnt_valueNext = 23'h0;
    end else begin
      domainArea_cnt_valueNext = (domainArea_cnt_value + _zz_domainArea_cnt_valueNext);
    end
    if(domainArea_cnt_willClear) begin
      domainArea_cnt_valueNext = 23'h0;
    end
  end

  assign io_led_r = domainArea_r;
  assign io_led_g = 1'b0;
  assign io_led_b = 1'b0;
  always @(posedge io_sys_clk or posedge _zz_1) begin
    if(_zz_1) begin
      domainArea_cnt_value <= 23'h0;
    end else begin
      domainArea_cnt_value <= domainArea_cnt_valueNext;
    end
  end

  always @(posedge io_sys_clk) begin
    if(domainArea_cnt_willOverflow) begin
      domainArea_r <= (! domainArea_r);
    end
  end


endmodule
