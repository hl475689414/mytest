/**
 * Copyright (c) 2013-2020, cpthack 成佩涛 (1044559878@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xqy.project.business.jiajiao.weixin.api.base;

import org.springframework.stereotype.Controller;

import com.jfinal.weixin.iot.msg.InEquDataMsg;
import com.jfinal.weixin.iot.msg.InEqubindEvent;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import com.jfinal.weixin.sdk.msg.in.InLinkMsg;
import com.jfinal.weixin.sdk.msg.in.InLocationMsg;
import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.in.InNotDefinedMsg;
import com.jfinal.weixin.sdk.msg.in.InShortVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVideoMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.card.InCardPassCheckEvent;
import com.jfinal.weixin.sdk.msg.in.card.InCardPayOrderEvent;
import com.jfinal.weixin.sdk.msg.in.card.InCardSkuRemindEvent;
import com.jfinal.weixin.sdk.msg.in.card.InMerChantOrderEvent;
import com.jfinal.weixin.sdk.msg.in.card.InUpdateMemberCardEvent;
import com.jfinal.weixin.sdk.msg.in.card.InUserCardEvent;
import com.jfinal.weixin.sdk.msg.in.card.InUserConsumeCardEvent;
import com.jfinal.weixin.sdk.msg.in.card.InUserGetCardEvent;
import com.jfinal.weixin.sdk.msg.in.card.InUserGiftingCardEvent;
import com.jfinal.weixin.sdk.msg.in.card.InUserPayFromCardEvent;
import com.jfinal.weixin.sdk.msg.in.event.InCustomEvent;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InLocationEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMassEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.in.event.InNotDefinedEvent;
import com.jfinal.weixin.sdk.msg.in.event.InPoiCheckNotifyEvent;
import com.jfinal.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent;
import com.jfinal.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.jfinal.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.jfinal.weixin.sdk.msg.in.event.InWifiEvent;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.OutCustomMsg;

/**
 * <b>AbstractWxMsgControllerAdapter.java</b></br>
 * 
 * <pre>
 * MsgControllerAdapter 对 MsgController 部分方法提供了默认实现，
 * 以便开发者不去关注 MsgController 中不需要处理的抽象方法，节省代码量
 * </pre>
 *
 * @author cpthack 1044559878@qq.com
 * @date Apr 28, 2018 11:04:57 AM
 * @since JDK 1.8
 */
@Controller
public abstract class AbstractWxMsgControllerAdapter extends AbstractWxMsgController {
	
	// 关注/取消关注事件
	@Override
	protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);
	
	// 接收文本消息事件
	@Override
	protected abstract void processInTextMsg(InTextMsg inTextMsg);
	
	// 自定义菜单事件
	@Override
	protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);
	
	// 接收图片消息事件
	@Override
	protected void processInImageMsg(InImageMsg inImageMsg) {
		renderCustomMsg(inImageMsg);
	}
	
	// 接收语音消息事件
	@Override
	protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
		renderCustomMsg(inVoiceMsg);
	}
	
	// 接收视频消息事件
	@Override
	protected void processInVideoMsg(InVideoMsg inVideoMsg) {
		renderCustomMsg(inVideoMsg);
	}
	
	// 接收地理位置消息事件
	@Override
	protected void processInLocationMsg(InLocationMsg inLocationMsg) {
		renderCustomMsg(inLocationMsg);
	}
	
	// 接收链接消息事件
	@Override
	protected void processInLinkMsg(InLinkMsg inLinkMsg) {
		renderCustomMsg(inLinkMsg);
	}
	
	// 扫描带参数二维码事件
	@Override
	protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		renderDefault();
	}
	
	// 上报地理位置事件
	@Override
	protected void processInLocationEvent(InLocationEvent inLocationEvent) {
		renderDefault();
	}
	
	// 接收语音识别结果，与 InVoiceMsg 唯一的不同是多了一个 Recognition 标记
	@Override
	protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
		renderCustomMsg(inSpeechRecognitionResults);
	}
	
	// 在模版消息发送任务完成后事件
	@Override
	protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
		renderDefault();
	}
	
	// 群发完成事件
	@Override
	protected void processInMassEvent(InMassEvent inMassEvent) {
		renderDefault();
	}
	
	// 接收小视频消息
	@Override
	protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
		renderCustomMsg(inShortVideoMsg);
	}
	
	// 接客服入会话事件
	@Override
	protected void processInCustomEvent(InCustomEvent inCustomEvent) {
		renderDefault();
	}
	
	// 用户进入摇一摇界面，在“周边”页卡下摇一摇时事件
	@Override
	protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {
		renderDefault();
	}
	
	// 资质认证事件
	@Override
	protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {
		renderDefault();
	}
	
	// 资质认证失败事件
	@Override
	protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {
		renderDefault();
	}
	
	// 门店在审核通过后下发消息事件
	@Override
	protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {
		renderDefault();
	}
	
	// WIFI连网后下发消息 by unas at 2016-1-29
	@Override
	protected void processInWifiEvent(InWifiEvent inWifiEvent) {
		renderDefault();
	}
	
	// 微信会员卡积分变更事件
	@Override
	protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent msg) {
		renderDefault();
	}
	
	// 微信会员卡快速买单事件
	@Override
	protected void processInUserPayFromCardEvent(InUserPayFromCardEvent msg) {
		renderDefault();
	}
	
	// 微信小店订单支付成功接口事件
	@Override
	protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {
		renderDefault();
	}
	
	// 没有找到对应的事件消息
	@Override
	protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {
		renderCustomMsg(inNotDefinedEvent);
	}
	
	// 没有找到对应的消息
	@Override
	protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {
		renderDefault();
	}
	
	@Override
	protected void processInUserGiftingCardEvent(InUserGiftingCardEvent msg) {
		renderDefault();
	}
	
	@Override
	protected void processInUserGetCardEvent(InUserGetCardEvent msg) {
		renderDefault();
	}
	
	@Override
	protected void processInUserConsumeCardEvent(InUserConsumeCardEvent msg) {
		renderDefault();
	}
	
	@Override
	protected void processInCardSkuRemindEvent(InCardSkuRemindEvent msg) {
		renderDefault();
	}
	
	@Override
	protected void processInCardPayOrderEvent(InCardPayOrderEvent msg) {
		renderDefault();
	}
	
	@Override
	protected void processInCardPassCheckEvent(InCardPassCheckEvent msg) {
		renderDefault();
	}
	
	@Override
	protected void processInUserCardEvent(InUserCardEvent inUserCardEvent) {
		renderDefault();
	}
	
	/**
	 * 处理微信硬件绑定和解绑事件
	 * 
	 * @param msg
	 *            处理微信硬件绑定和解绑事件
	 */
	@Override
	protected void processInEqubindEvent(InEqubindEvent msg) {
		renderDefault();
	}
	
	/**
	 * 处理微信硬件发来数据
	 * 
	 * @param msg
	 *            处理微信硬件发来数据
	 */
	@Override
	protected void processInEquDataMsg(InEquDataMsg msg) {
		renderDefault();
	}
	
	/**
	 * 方便没有使用的api返回“”避免出现，该公众号暂时不能提供服务
	 *
	 * 可重写该方法
	 */
	protected void renderDefault() {
		renderText("");
	}
	
	/**
	 * 默认将消息都转发给多客服PC客户端
	 *
	 * 可重写该方法
	 */
	protected void renderCustomMsg(InMsg msg) {
		OutCustomMsg outCustomMsg = new OutCustomMsg(msg);
		render(outCustomMsg);
	}
	
}
