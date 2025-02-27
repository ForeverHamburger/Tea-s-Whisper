package com.example.module.chat.base.database;

class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;

    public int getPrompt_tokens() {
        return prompt_tokens;
    }

    public int getCompletion_tokens() {
        return completion_tokens;
    }

    public int getTotal_tokens() {
        return total_tokens;
    }
}
