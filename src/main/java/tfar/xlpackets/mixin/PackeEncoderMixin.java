package tfar.xlpackets.mixin;

import net.minecraft.network.PacketEncoder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PacketEncoder.class)
public class PackeEncoderMixin {
	@ModifyConstant(method = "encode(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;Lio/netty/buffer/ByteBuf;)V",constant = @Constant(intValue = 8388608))
	private int xlPackets(int old) {
		return 2000000000;
	}
}
