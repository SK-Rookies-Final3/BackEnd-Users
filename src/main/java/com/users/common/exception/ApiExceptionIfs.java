package com.users.common.exception;

import com.users.common.error.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();
    String getErrorDescription();
}
