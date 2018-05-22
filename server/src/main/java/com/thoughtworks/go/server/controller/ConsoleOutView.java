/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.server.controller;

import com.thoughtworks.go.domain.ConsoleConsumer;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

public class ConsoleOutView implements View {
    private ConsoleConsumer consumer;
    private final Charset charset;

    public ConsoleOutView(ConsoleConsumer consumer, Charset charset) {
        this.consumer = consumer;
        this.charset = charset;
    }

    public String getContentType() {
        return "text/plain; charset=" + charset;
    }

    public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(getContentType());
        response.setCharacterEncoding(charset.name());
        try (final PrintWriter writer = response.getWriter()) {
            try {
                consumer.stream(line -> writer.write(line + "\n"));
            } catch (IOException e) {
                if (e instanceof FileNotFoundException) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    throw e;
                }
            }
        } finally {
            consumer.close();
        }
    }
}
