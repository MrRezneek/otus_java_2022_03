package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProcessorThrowErrorTest {
    @Test
    void processorTest() {
        var processorThrowError = new ProcessorThrowError(() -> LocalDateTime.of(2022, 7, 10, 0, 0, 4));
        var message = new Message.Builder(1L).field7("field7").build();
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processorThrowError.process(message));
    }
}
