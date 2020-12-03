/**
 * MIT License
 *
 * Copyright (c) 2018 Anatoly Gudkov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.green.jelly;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JsonEventPumpTest {

    private static final String TEST_JSON = "{"
        + "\"a\": 1,"
        + "\"b\": null,"
        + "\"c\": [true, false, null, 1, \"string\", "
        + "{ \"cc_1\": [], \"cc_2\": [1, 2, 4], \"cc_3\": \"cc_3 value\"}]"
        + "}";

    @Test
    public void test() {
        final JsonParser parser = new JsonParser(new CopyingStringBuilder(/*false*/));

        final JsonEvents expectedEvents = new JsonEvents();
        parser.setListener(expectedEvents);

        parser.parseAndEoj(TEST_JSON);

        final StringBuilder resultJson = new StringBuilder();

        final JsonGenerator generator = new JsonGenerator(new AppendableWriter<>(resultJson));
        final JsonEventPump pump = new JsonEventPump(generator);
        parser.setListener(pump);

        parser.parseAndEoj(TEST_JSON);

        final JsonEvents resultEvents = new JsonEvents();
        parser.setListener(resultEvents);

        parser.parseAndEoj(resultJson);

        assertEquals(expectedEvents, resultEvents);
    }
}
