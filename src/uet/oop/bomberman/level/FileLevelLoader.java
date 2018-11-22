package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) throws LoadLevelException {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		try {
			URL absPath = FileLevelLoader.class.getResource("/levels/Level" + level + ".txt");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(absPath.openStream()));

			String basicData = bufferedReader.readLine();
			StringTokenizer token = new StringTokenizer(basicData);

			_level = Integer.parseInt(token.nextToken());
			_height = Integer.parseInt(token.nextToken());
			_width = Integer.parseInt(token.nextToken());

			_map = new char[_height][_width];
			String[] line = new String[_height];
			// char[][] copyMap = new char[_height][_width];
			// la mang cac String chua tung hang tren ban do
			for (int i = 0; i < _height; i++) {
				line[i] = bufferedReader.readLine().substring(0, _width);
				for (int j = 0 ; j < _width; j++) {
					_map[i][j] = line[i].charAt(j);

				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			throw new LoadLevelException("Error loading level " + level, e);
		}
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall

		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				//int pos = x + y * _width;
				//Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
				//_board.addEntity(pos, new Grass(x, y, sprite));
				addEntityToMap(_map[y][x], x, y);

			}
		}
	}

	public void addEntityToMap(char c, int x, int y) {
		int pos = x + y*getWidth();

		switch(c) {
			case '#': // wall
				_board.addEntity(pos, new Wall(x, y, Sprite.wall));
				break;
			case '*': // brick
				_board.addEntity( pos, new LayeredEntity( x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick) ) );
				break;
			case ' ': // grass
				_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
				break;
			case 'p': // bomberman
				_board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
				Screen.setOffset(0, 0);
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '1': // balloon
				_board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case '2': // Oneal
				_board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));
				break;
			case 'b': // BombItem
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
						new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick) ) );
				break;
			case 'f': // FlameItem
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
						new FlameItem(x, y, Sprite.powerup_flames), new Brick(x, y, Sprite.brick) ) );
				break;
			case 's': // SpeedItem
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass),
						new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick) ) );
				break;
			case 'x': // portal
				_board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal),
						new Brick(x, y, Sprite.brick) ) );
				break;
			default:
				_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
				break;
		}
	}

}
