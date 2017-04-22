package com.ls.ls.audiopush;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ls.ls.R;

import cn.nodemedia.LivePublisher;
import cn.nodemedia.LivePublisherDelegate;

public class AudioStream extends AppCompatActivity implements LivePublisherDelegate {
    private TextView tv;
    private Button startstop;
    private boolean isStarting = false;
    private String puburl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiostream);
        puburl = "rtmp://139.199.224.141:1930/live/android";
        tv = (TextView) findViewById(R.id.tv);
        startstop = (Button) findViewById(R.id.btn_startstop);


        LivePublisher.init(this); // 1.初始化
        LivePublisher.setDelegate(this); // 2.设置事件回调
        /**
         * 设置输出音频参数 bitrate 码率 32kbps aacProfile 音频编码复杂度
         * 部分服务端不支持HE-AAC,会导致发布失败，如果服务端支持，直接用HE-AAC
         * AAC_PROFILE_LC 低复杂度编码
         * AAC_PROFILE_HE 高效能编码 ，能达到LC-AAC一半的码率传输相同的音质
         */
        LivePublisher.setAudioParam(32 * 1000, LivePublisher.AAC_PROFILE_HE);

        /**
         * 是否开启背景噪音抑制
         */
        LivePublisher.setDenoiseEnable(true);

        /**
         * 开始视频预览 cameraPreview ： 用以回显摄像头预览的GLSurfaceView对象，如果此参数传入null，则只发布音频
         * 注意,如果做纯音频应用,请保证xml布局文件里不要放入GLSurfaceView控件,否则由于没有处理GLSurfaceView的渲染器会造成崩溃
         *
         * camId： 摄像头初始id，LivePublisher.CAMERA_BACK 后置，LivePublisher.CAMERA_FRONT 前置
         * frontMirror: 是否启用前置摄像头镜像模式。当为true时，预览画面为镜像画面。当为false时，预览画面为原始画面
         * 镜像画面就是平时使用系统照相机切换前置摄像头时所显示的画面，就像自己照镜子看到的画面。 原始画面就是最终保存或传输到观看者所显示的画面。
         */
        LivePublisher.startPreview(null, LivePublisher.CAMERA_FRONT, true);



        startstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStarting){
                    LivePublisher.stopPublish();//停止发布
                    tv.setText("");
                    startstop.setText("开始推流");
                }
                else {
                    LivePublisher.startPublish(puburl);
                    tv.setText(puburl);
                    startstop.setText("停止推流");
                }
            }
        });
    }









    @Override
    public void onEventCallback(int event, String msg) {
        handler.sendEmptyMessage(event);
    }

    private Handler handler = new Handler() {
        // 回调处理
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2000:
                    Toast.makeText(AudioStream.this, "正在发布视频", Toast.LENGTH_SHORT).show();
                    break;
                case 2001:
                    Toast.makeText(AudioStream.this, "视频发布成功", Toast.LENGTH_SHORT).show();
//                    videoBtn.setBackgroundResource(R.drawable.ic_video_start);
                    isStarting = true;
                    break;
                case 2002:
                    Toast.makeText(AudioStream.this, "视频发布失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2004:
                    Toast.makeText(AudioStream.this, "视频发布结束", Toast.LENGTH_SHORT).show();
//                    videoBtn.setBackgroundResource(R.drawable.ic_video_stop);
                    isStarting = false;
                    break;
                case 2005:
                    Toast.makeText(AudioStream.this, "网络异常,发布中断", Toast.LENGTH_SHORT).show();
                    break;
                case 2100:
                    // 发布端网络阻塞，已缓冲了2秒的数据在队列中
                    Toast.makeText(AudioStream.this, "网络阻塞，发布卡顿", Toast.LENGTH_SHORT).show();
                    break;
                case 2101:
                    // 发布端网络恢复畅通
                    Toast.makeText(AudioStream.this, "网络恢复，发布流畅", Toast.LENGTH_SHORT).show();
                    break;
                case 2102:
                    Toast.makeText(AudioStream.this, "截图保存成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2103:
                    Toast.makeText(AudioStream.this, "截图保存失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2104:
                    Toast.makeText(AudioStream.this, "网络阻塞严重,无法继续推流,断开连接", Toast.LENGTH_SHORT).show();
                    break;
                case 2300:
                    Toast.makeText(AudioStream.this, "摄像头和麦克风都不能打开,用户没有给予访问权限或硬件被占用", Toast.LENGTH_SHORT).show();
                    break;
                case 2301:
                    Toast.makeText(AudioStream.this, "麦克风无法打开", Toast.LENGTH_SHORT).show();
                    break;
                case 2302:
                    Toast.makeText(AudioStream.this, "摄像头无法打开", Toast.LENGTH_SHORT).show();
                    break;
                case 3100:
                    // mic off
//                    micBtn.setBackgroundResource(R.drawable.ic_mic_off);
                    Toast.makeText(AudioStream.this, "麦克风静音", Toast.LENGTH_SHORT).show();
                    break;
                case 3101:
                    // mic on
//                    micBtn.setBackgroundResource(R.drawable.ic_mic_on);
                    Toast.makeText(AudioStream.this, "麦克风恢复", Toast.LENGTH_SHORT).show();
                    break;
                case 3102:
                    // camera off
//                    camBtn.setBackgroundResource(R.drawable.ic_cam_off);
                    Toast.makeText(AudioStream.this, "摄像头传输关闭", Toast.LENGTH_SHORT).show();
                    break;
                case 3103:
                    // camera on
//                    camBtn.setBackgroundResource(R.drawable.ic_cam_on);
                    Toast.makeText(AudioStream.this, "摄像头传输打开", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
