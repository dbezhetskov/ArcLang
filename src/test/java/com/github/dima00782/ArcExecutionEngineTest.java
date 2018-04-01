package com.github.dima00782;

import com.github.dima00782.interpreter.Dumper;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;

public class ArcExecutionEngineTest {
    private static ArcExecutionEngine engine;

    @BeforeClass
    public static void initEngine() {
        engine = new ArcExecutionEngine();
    }

    private static String execute(CharStream charStream) {
        StringBuilder sb = new StringBuilder();
        engine.execute(charStream, new Dumper() {
            @Override
            public synchronized void dump(String string) {
                sb.append(string).append(" ");
            }
        });

        return sb.toString();
    }

    @Test
    public void testAllCommands() throws IOException {
        URL url = this.getClass().getResource("/all_commands.arc");
        CharStream charStream = CharStreams.fromStream(new FileInputStream(url.getFile()));

        assertEquals("ArcObject{refCount=1, fields={}} " +
                "ArcObject{refCount=1, fields={" +
                "x=ArcObject{refCount=1, fields={}}=false, " +
                "y=ArcObject{refCount=1, fields={}}=false, " +
                "z=ArcObject{refCount=1, fields={}}=false}} " +
                "null ", execute(charStream));
    }

    @Test(expected = NullPointerException.class)
    public void testException() throws IOException {
        URL url = this.getClass().getResource("/raise_exception.arc");
        CharStream charStream = CharStreams.fromStream(new FileInputStream(url.getFile()));

        execute(charStream);
    }
}