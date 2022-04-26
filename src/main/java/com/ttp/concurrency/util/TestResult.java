package com.ttp.concurrency.util;

public record TestResult<T>(T result, Long runtime) {
}
