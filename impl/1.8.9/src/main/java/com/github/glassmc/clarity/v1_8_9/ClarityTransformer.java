package com.github.glassmc.clarity.v1_8_9;

import com.github.glassmc.loader.loader.ITransformer;
import com.github.glassmc.loader.util.Identifier;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class ClarityTransformer implements ITransformer {

    private final Identifier MINECRAFT_CLIENT = Identifier.parse("net/minecraft/client/MinecraftClient");
    private final Identifier RUN_GAME_LOOP = Identifier.parse("net/minecraft/client/MinecraftClient#runGameLoop()V");

    private final Identifier CHUNK_BUILDER = Identifier.parse("net/minecraft/client/render/chunk/ChunkBuilder");

    private final Identifier CHUNK_RENDER_THREAD = Identifier.parse("net/minecraft/client/world/ChunkRenderThread");
    private final Identifier METHOD_3793 = Identifier.parse("net/minecraft/client/world/ChunkRenderThread#method_3793(Lnet/minecraft/client/world/ChunkBuilder;)V");

    private final Identifier CLIPPER = Identifier.parse("net/minecraft/client/util/Clipper");
    private final Identifier START = Identifier.parse("net/minecraft/client/util/Clipper#start()V");

    @Override
    public byte[] transform(String name, byte[] data) {
        if(name.equals(MINECRAFT_CLIENT.getClassName())) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(data);
            classReader.accept(classNode, 0);

            for (MethodNode methodNode : classNode.methods) {
                if (methodNode.name.equals(RUN_GAME_LOOP.getMethodName()) && methodNode.desc.equals(RUN_GAME_LOOP.getMethodDesc())) {
                    methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.class.getName().replace(".", "/"), "test1", "()V"));
                }
            }

            ClassWriter classWriter = new ClassWriter(0);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }

        if(name.equals(CHUNK_BUILDER.getClassName())) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(data);
            classReader.accept(classNode, 0);

            for(MethodNode methodNode : classNode.methods) {
                if(methodNode.name.equals("<init>")) {
                    for(AbstractInsnNode node : methodNode.instructions.toArray()) {
                        if(node instanceof InsnNode && node.getOpcode() == Opcodes.ICONST_2) {
                            AbstractInsnNode previous = node.getPrevious();
                            methodNode.instructions.remove(node);
                            methodNode.instructions.insert(previous, new InsnNode(Opcodes.ICONST_1));
                        }
                    }
                }
            }

            ClassWriter classWriter = new ClassWriter(0);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }

        if(name.equals(CHUNK_RENDER_THREAD.getClassName())) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(data);
            classReader.accept(classNode, 0);

            for(MethodNode methodNode : classNode.methods) {
                if(methodNode.name.equals(METHOD_3793.getMethodName()) && methodNode.desc.equals(METHOD_3793.getMethodDesc())) {
                    methodNode.instructions.insert(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.class.getName().replace(".", "/"), "applyChunkCooldown", "()V"));
                }
            }

            ClassWriter classWriter = new ClassWriter(0);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }

        if(name.equals(CLIPPER.getClassName())) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(data);
            classReader.accept(classNode, 0);

            for(MethodNode methodNode : classNode.methods) {

                if(methodNode.name.equals(START.getMethodName()) && methodNode.desc.equals(START.getMethodDesc())) {
                    InsnList insnList = new InsnList();

                    LabelNode labelNode = new LabelNode();
                    insnList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Hook.class.getName().replace(".", "/"), "skipFrustumUpdate", "()Z"));
                    insnList.add(new JumpInsnNode(Opcodes.IFEQ, labelNode));
                    insnList.add(new InsnNode(Opcodes.RETURN));
                    insnList.add(labelNode);

                    methodNode.instructions.insert(insnList);
                }
            }

            ClassWriter classWriter = new ClassWriter(0);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        return data;
    }

}
