package com.winit.cloudlink.console.task.model;

public class ShovelCheck {

    private String  srcArea;

    private String  destArea;

    private String  srcURI;

    private boolean srcURIRight;

    private String  destURI;

    private boolean destURIRight;

    private String  srcQueueName;

    private String  destExchageName;

    private String  shovelName;

    private boolean srcQueueExists;

    private boolean srcQueueBinding;

    private boolean srcQueueDurable;

    private boolean srcQueueAutoDelete = true;

    private boolean destExchageExists = true;

    private boolean shovelExists;

    private boolean running;

    private boolean neverAutoDelete;

    private boolean onConfirm;

    public ShovelCheck(){
    }

    public String getSrcArea() {
        return srcArea;
    }

    public void setSrcArea(String srcArea) {
        this.srcArea = srcArea;
    }

    public String getDestArea() {
        return destArea;
    }

    public void setDestArea(String destArea) {
        this.destArea = destArea;
    }

    public String getSrcQueueName() {
        return srcQueueName;
    }

    public void setSrcQueueName(String srcQueueName) {
        this.srcQueueName = srcQueueName;
    }

    public String getDestExchageName() {
        return destExchageName;
    }

    public void setDestExchageName(String destExchageName) {
        this.destExchageName = destExchageName;
    }

    public String getShovelName() {
        return shovelName;
    }

    public void setShovelName(String shovelName) {
        this.shovelName = shovelName;
    }

    public boolean isSrcQueueError() {
        return srcQueueExists;
    }

    public void setDestExchageExists(boolean destExchageExists) {
        this.destExchageExists = destExchageExists;
    }

    public boolean isShovelExists() {
        return shovelExists;
    }

    public void setShovelExists(boolean shovelExists) {
        this.shovelExists = shovelExists;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isNeverAutoDelete() {
        return neverAutoDelete;
    }

    public void setNeverAutoDelete(boolean neverAutoDelete) {
        this.neverAutoDelete = neverAutoDelete;
    }

    public boolean isOnConfirm() {
        return onConfirm;
    }

    public void setOnConfirm(boolean onConfirm) {
        this.onConfirm = onConfirm;
    }

    public String getSrcURI() {
        return srcURI;
    }

    public void setSrcURI(String srcURI) {
        this.srcURI = srcURI;
    }

    public String getDestURI() {
        return destURI;
    }

    public void setDestURI(String destURI) {
        this.destURI = destURI;
    }

    public boolean isSrcQueueExists() {
        return srcQueueExists;
    }

    public void setSrcQueueExists(boolean srcQueueExists) {
        this.srcQueueExists = srcQueueExists;
    }

    public boolean isSrcQueueBinding() {
        return srcQueueBinding;
    }

    public void setSrcQueueBinding(boolean srcQueueBinding) {
        this.srcQueueBinding = srcQueueBinding;
    }

    public boolean isSrcQueueDurable() {
        return srcQueueDurable;
    }

    public void setSrcQueueDurable(boolean srcQueueDurable) {
        this.srcQueueDurable = srcQueueDurable;
    }

    public boolean isSrcQueueAutoDelete() {
        return srcQueueAutoDelete;
    }

    public void setSrcQueueAutoDelete(boolean srcQueueAutoDelete) {
        this.srcQueueAutoDelete = srcQueueAutoDelete;
    }

    public boolean isDestExchageExists() {
        return destExchageExists;
    }

    public boolean isSrcURIRight() {
        return srcURIRight;
    }

    public void setSrcURIRight(boolean srcURIRight) {
        this.srcURIRight = srcURIRight;
    }

    public boolean isDestURIRight() {
        return destURIRight;
    }

    public void setDestURIRight(boolean destURIRight) {
        this.destURIRight = destURIRight;
    }

    /**
     * 返回true表示shovel配置正确
     * 
     * @return
     */
    public boolean isExceptonShovel() {
        if (this.srcQueueExists && this.srcQueueBinding && this.srcQueueDurable && !this.srcQueueAutoDelete
            && this.destExchageExists && this.shovelExists && this.running && this.neverAutoDelete && this.onConfirm
            && this.srcURIRight && this.destURIRight) {
            return false;
        }
        return true;
    }
}
