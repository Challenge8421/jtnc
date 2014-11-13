package de.hsbremen.tc.tnc.im;

import org.junit.Before;
import org.junit.Test;
import org.trustedcomputinggroup.tnc.ifimv.IMV;
import org.trustedcomputinggroup.tnc.ifimv.TNCException;

import de.hsbremen.tc.tnc.im.adapter.connection.enums.ImConnectionStateEnum;
import de.hsbremen.tc.tnc.im.adapter.data.ImRawComponent;
import de.hsbremen.tc.tnc.im.adapter.imv.ImvAdapterIetf;
import de.hsbremen.tc.tnc.im.adapter.imv.ImvAdapterIetfLong;

public class ImvTest {

	IMV imv;
	IMV imvLong;
	
	@Before
	public void setUp(){
		Dummy.setLogSettings();
		this.imv = new ImvAdapterIetf();
		this.imvLong = new ImvAdapterIetfLong();
		
	}
	
	@Test
	public void testInitialize() throws TNCException{
		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(),"Test initialize method."));
		this.initializeImv();
	}
	
	@Test(expected = TNCException.class)
	public void testDoubleInitialize() throws TNCException{
		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(),"Test error for redundant initialization."));
		try{
			this.initializeImv();
			this.initializeImv();
		}catch(TNCException e){
			if(e.getResultCode() == TNCException.TNC_RESULT_ALREADY_INITIALIZED){
				throw e;
			}else{
				System.err.println("Unexpected error was thrown. " +  e.getMessage());
			}
		}
	}
	
	@Test(expected = TNCException.class)
	public void testNotInitialized() throws TNCException{
		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(), "Test error for missing initialization."));
		try{
			this.imv.notifyConnectionChange(Dummy.getIMVConnection(),ImConnectionStateEnum.TNC_CONNECTION_STATE_CREATE.state());
		}catch(TNCException e){
			if(e.getResultCode() == TNCException.TNC_RESULT_NOT_INITIALIZED){
				throw e;
			}else{
				System.err.println("Unexpected error was thrown. " +  e.getMessage());
			}
		}
	}
	
	@Test
	public void testNotifyConnectionChange() throws TNCException{
		
		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(), "Test notify connection change with CREATE."));
		try {
			this.initializeImv();
		} catch (TNCException e) {
			e.printStackTrace();
		}
		
		this.imv.notifyConnectionChange(Dummy.getIMVConnection(), ImConnectionStateEnum.TNC_CONNECTION_STATE_CREATE.state());
	}
	
//	@Test
//	public void testBeginHandshake() throws TNCException{
//
//		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(), "Test begin handshake."));
//		try {
//			this.initializeImv();
//		} catch (TNCException e) {
//			e.printStackTrace();
//		}
//		this.imv.beginHandshake(Dummy.getIMVConnection());
//		
//	}
	
	@Test
	public void testBatchEnding() throws TNCException{

		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(), "Test batch ending."));
		try {
			this.initializeImv();
		} catch (TNCException e) {
			e.printStackTrace();
		}
		this.imv.batchEnding(Dummy.getIMVConnection());
		
	}
	
	@Test
	public void testReceiveMessage() throws TNCException{
		System.out.println(Dummy.getTestDescriptionHead(this.getClass().getSimpleName(), "Test receive message."));
		ImRawComponent component = Dummy.getRawComponentForImv();
		long messageType = (component.getVendorId() << 8) | (component.getType() & 0xFF);
		
		try {
			this.initializeImv();
		} catch (TNCException e) {
			e.printStackTrace();
		}

		this.imv.receiveMessage(Dummy.getIMVConnection(),messageType, component.getMessage());
	}
		
	
	private void initializeImv() throws TNCException{
		this.imv.initialize(Dummy.getTncs());
//		if(this.imc instanceof AttributeSupport){
//			((AttributeSupport) imc).setAttribute(AttributeSupport.TNC_ATTRIBUTEID_PRIMARY_IMC_ID, new Long(new Random().nextInt(100)));
//		}
	}
	
	
}
