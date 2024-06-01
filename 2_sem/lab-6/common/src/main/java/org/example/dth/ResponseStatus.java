
package org.example.dth;

import java.io.Serializable;

public enum ResponseStatus implements Serializable {
    OK,
    ASK_OBJECT,
    EXIT,
    ERROR,
    WRONG_ARGUMENTS,
}
