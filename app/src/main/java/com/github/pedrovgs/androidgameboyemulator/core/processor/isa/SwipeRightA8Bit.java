/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.github.pedrovgs.androidgameboyemulator.core.processor.isa;

import com.github.pedrovgs.androidgameboyemulator.core.mmu.MMU;
import com.github.pedrovgs.androidgameboyemulator.core.processor.GBZ80;

abstract class SwipeRightA8Bit extends Instruction {

  SwipeRightA8Bit(GBZ80 z80) {
    super(z80);
  }

  SwipeRightA8Bit(GBZ80 z80, MMU mmu) {
    super(z80, mmu);
  }

  @Override public void execute() {
    byte value = loadValue();
    boolean shouldEnableFlagCY = (value & 0x1) == 0x1;
    byte previousSeventhBitValue = (byte) (value & 0x80);
    value >>= 1;
    value |= previousSeventhBitValue;
    storeValue(value);
    z80.resetFlagF();
    if (shouldEnableFlagCY) {
      z80.enableFlagCY();
    }
    if (value == 0) {
      z80.enableFlagZ();
    }
    setLastInstructionExecutionTime();
  }

  protected abstract byte loadValue();

  protected abstract void storeValue(byte value);

  protected abstract void setLastInstructionExecutionTime();
}
