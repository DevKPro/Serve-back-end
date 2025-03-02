package pers.leanfeng.score.exception.http;

public class ServerErrorException extends HttpException{
    public ServerErrorException(int code){
        this.httpStatusCode=500;
        this.code=code;
    }
}
