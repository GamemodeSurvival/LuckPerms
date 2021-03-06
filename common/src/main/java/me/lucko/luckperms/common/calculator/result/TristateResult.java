/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.luckperms.common.calculator.result;

import me.lucko.luckperms.api.Tristate;
import me.lucko.luckperms.common.calculator.PermissionCalculator;
import me.lucko.luckperms.common.calculator.processor.PermissionProcessor;

/**
 * Represents the result of a {@link PermissionCalculator} lookup.
 */
public final class TristateResult {

    public static final TristateResult UNDEFINED = new TristateResult(Tristate.UNDEFINED, null, null);

    public static TristateResult of(Tristate result, Class<? extends PermissionProcessor> processorClass, String cause) {
        if (result == Tristate.UNDEFINED) {
            return UNDEFINED;
        }
        return new TristateResult(result, processorClass, cause);
    }

    public static TristateResult of(Tristate result, Class<? extends PermissionProcessor> processorClass) {
        return of(result, processorClass, null);
    }

    public static TristateResult of(Tristate result) {
        return of(result, null, null);
    }
    
    private final Tristate result;
    private final Class<? extends PermissionProcessor> processorClass;
    private final String cause;

    private TristateResult(Tristate result, Class<? extends PermissionProcessor> processorClass, String cause) {
        this.result = result;
        this.processorClass = processorClass;
        this.cause = cause;
    }

    public Tristate result() {
        return this.result;
    }

    public Class<? extends PermissionProcessor> processorClass() {
        return this.processorClass;
    }

    public String cause() {
        return this.cause;
    }

    public static final class Factory {
        private final Class<? extends PermissionProcessor> processorClass;

        private final TristateResult trueResult;
        private final TristateResult falseResult;

        public Factory(Class<? extends PermissionProcessor> processorClass) {
            this.processorClass = processorClass;

            this.trueResult = of(Tristate.TRUE, processorClass);
            this.falseResult = of(Tristate.FALSE, processorClass);
        }

        public TristateResult result(Tristate result) {
            switch (result) {
                case TRUE:
                    return this.trueResult;
                case FALSE:
                    return this.falseResult;
                case UNDEFINED:
                    return UNDEFINED;
                default:
                    throw new AssertionError();
            }
        }

        public TristateResult result(Tristate result, String cause) {
            if (cause == null) {
                return result(result);
            }
            return of(result, this.processorClass, cause);
        }
    }
}
