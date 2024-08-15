package club.someoneice.pineapplepsychic.util.math;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;

import java.util.Objects;

@SuppressWarnings("unused")
public final class Vector3D {
    public static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    public static final Vector3D XN = new Vector3D(-1.0, 0.0, 0.0);
    public static final Vector3D XP = new Vector3D(1.0, 0.0, 0.0);
    public static final Vector3D YN = new Vector3D(0.0, -1.0, 0.0);
    public static final Vector3D YP = new Vector3D(0.0, 1.0, 0.0);
    public static final Vector3D ZN = new Vector3D(0.0, 0.0, -1.0);
    public static final Vector3D ZP = new Vector3D(0.0, 0.0, 1.0);
    public double x;
    public double y;
    public double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3D fromPos(ChunkPosition pos) {
        Vector3D ret = Vector3D.ZERO;
        ret.x = pos.chunkPosX;
        ret.y = pos.chunkPosY;
        ret.z = pos.chunkPosZ;
        return ret;
    }

    public Vector3D vectorTo(Vector3D vec3d) {
        Vector3D ret = Vector3D.ZERO;
        ret.x = vec3d.x - this.x;
        ret.y = vec3d.y - this.y;
        ret.z = vec3d.z - this.z;
        return ret;
    }

    public Vector3D cross(Vector3D vec3d) {
        Vector3D ret = Vector3D.ZERO;
        ret.x = this.y * vec3d.z - this.z * vec3d.y;
        ret.y = this.z * vec3d.x - this.x * vec3d.z;
        ret.z = this.x * vec3d.y - this.y * vec3d.x;
        return ret;
    }

    public void add(Vector3D vec3d) {
        add(vec3d.x, vec3d.y, vec3d.z);
    }

    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void sub(double x, double y, double z) {
        add(-x, -y, -z);
    }

    public void sub(Vector3D vec3d) {
        sub(vec3d.x, vec3d.y, vec3d.z);
    }

    public Vector3D copy() {
        return new Vector3D(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3D vector3D = (Vector3D) o;
        return Objects.equals(x, vector3D.x) && Objects.equals(y, vector3D.y) && Objects.equals(z, vector3D.z);
    }

    public ChunkPosition toPos() {
        return new ChunkPosition(MathHelper.floor_double(this.x), MathHelper.floor_double(this.y), MathHelper.floor_double(this.z));
    }

    public AxisAlignedBB asAABB(double distance) {
        return AxisAlignedBB.getBoundingBox(this.x - distance, this.y - distance, this.z - distance, this.x + distance, this.y + distance, this.z + distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
