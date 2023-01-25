package tfar.xlpackets.mixin;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.SplitterHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(SplitterHandler.class)
public class SplitterHandlerMixin {

	/**
	 * @author AngelBottomless
	 * @reason Maybe inflate packet
	 */
	@Overwrite
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> objects){
		buf.markReaderIndex();
		byte[] bs = new byte[4];

		for(int i = 0; i < bs.length; ++i) {
			if (!buf.isReadable()) {
				buf.resetReaderIndex();
				return;
			}

			bs[i] = buf.readByte();
			if (bs[i] >= 0) {
				PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.wrappedBuffer(bs));

				try {
					int j = packetByteBuf.readVarInt();
					if (buf.readableBytes() >= j) {
						objects.add(buf.readBytes(j));
						return;
					}

					buf.resetReaderIndex();
				} finally {
					packetByteBuf.release();
				}

				return;
			}
		}

		throw new CorruptedFrameException("length wider than 28-bit");
	}

}
