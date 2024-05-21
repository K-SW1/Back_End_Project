package ksw.BackEnd.RecallQuest.jwt.exception;

import java.util.NoSuchElementException;

public class NoSuchTokenException extends NoSuchElementException {
    public NoSuchTokenException(String message) {
        super(message);
    }
}
