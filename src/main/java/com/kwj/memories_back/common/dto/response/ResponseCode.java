package com.kwj.memories_back.common.dto.response;

public interface ResponseCode {
    String SUCCESS = "SU"; // public abstract 빠져잇는거 알지?, 상수라서 이렇게 씀

    String VALIDATION_FAIL = "VF";
    String EXIST_USER = "EU";
    String NO_EXIST_DIARY = "ND";

    String SIGN_IN_FAIL = "SF";

    
    String NO_PERMISSION = "NP"; 
    
    String DATABASE_ERROR = "DBE";
    
    String OPEN_AI_ERROR = "OAE";
}
