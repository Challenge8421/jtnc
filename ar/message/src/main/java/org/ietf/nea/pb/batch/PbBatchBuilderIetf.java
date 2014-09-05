package org.ietf.nea.pb.batch;

import java.util.LinkedList;
import java.util.List;

import org.ietf.nea.IETFConstants;
import org.ietf.nea.pb.batch.enums.PbBatchDirectionalityEnum;
import org.ietf.nea.pb.batch.enums.PbBatchTypeEnum;
import org.ietf.nea.pb.message.PbMessage;

public class PbBatchBuilderIetf {

	private PbBatchTypeEnum type;
	private PbBatchDirectionalityEnum direction;
	private List<PbMessage> messages;
	private long batchLength;
	
	
	public PbBatchBuilderIetf(){
		this.messages = new LinkedList<>();
		this.batchLength = PbBatch.FIXED_LENGTH;
	}
	
	public PbBatchBuilderIetf setBatchDirection(PbBatchDirectionalityEnum direction){
		
		if(type != null){
			if(direction == PbBatchDirectionalityEnum.TO_PBS && (type == PbBatchTypeEnum.RESULT || type == PbBatchTypeEnum.SRETRY || type == PbBatchTypeEnum.SDATA)){
				throw new IllegalStateException("Current type "+type.toString()+" can not be used with the supplied direction.");
			}
			if(direction == PbBatchDirectionalityEnum.TO_PBC && (type == PbBatchTypeEnum.CDATA || type == PbBatchTypeEnum.CRETRY)){
				throw new IllegalStateException("Current type "+ type.toString() +" can not be used with the supplied direction.");
			}
		}
		
		this.direction = direction;
		
		return this;
	}
	
	public PbBatchBuilderIetf setBatchType(PbBatchTypeEnum type){

		if(direction != null){
			if(direction == PbBatchDirectionalityEnum.TO_PBS && (type == PbBatchTypeEnum.RESULT || type == PbBatchTypeEnum.SRETRY || type == PbBatchTypeEnum.SDATA)){
				throw new IllegalStateException("Current direction "+type.toString()+" can not be used with the supplied type.");
			}
	
			if(direction == PbBatchDirectionalityEnum.TO_PBC && (type == PbBatchTypeEnum.CDATA || type == PbBatchTypeEnum.CRETRY)){
				throw new IllegalStateException("Current direction "+ type.toString() +" can not be used with the supplied type.");
			}
		}
		
		this.type = type;
		
		return this;
	}
	
	public PbBatchBuilderIetf addMessage(PbMessage message){
		if(message != null){
			this.addMessageAndCheckLength(message);
		}
		
		return this;
	}
	
	public PbBatchBuilderIetf addAllMessages(List<PbMessage> messages){
		if(messages != null){
			for (PbMessage pbMessage : messages) {
				this.addMessage(pbMessage);
			}
		}
		
		return this;
	}
	
	public PbBatch toBatch(){
		int reserved = 0; // defined in RFC5793 
		if(direction == null){
			throw new IllegalStateException("Direction must be set first.");
		}
		if(type == null){
			throw new IllegalStateException("Type must be set first.");
		}
		
		PbBatch batch = new PbBatch(direction, reserved, type, messages);
		return batch;
	}
	
	private void addMessageAndCheckLength(PbMessage message){
			long messageLength = message.getLength();
			if(messageLength > 0 && (IETFConstants.MAX_LENGTH - messageLength) < this.batchLength){
				throw new ArithmeticException("Batch size is to large.");
			}
			this.batchLength += messageLength;
			this.messages.add(message);
	}
}
