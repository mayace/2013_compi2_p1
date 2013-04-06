/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.CompilerSystem;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

/**
 *
 * @author ce
 */
public abstract class DirWatcher extends Thread {

  private Path dir;

  public DirWatcher(Path dir) {
    this.dir = dir;
  }

  @Override
  public final void run() {
    preRun();
    onRun();
    postRun();
  }

  public void preRun() {
  }

  private void onRun() {
    Path d = getDir();
    if (d != null && Files.exists(d) && Files.isDirectory(d)) {

      try {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        d.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        for (;;) {
          WatchKey taked = watcher.take();
          List<WatchEvent<?>> pollEvents = taked.pollEvents();

          for (WatchEvent<?> e : pollEvents) {
            WatchEvent.Kind<?> kind = e.kind();
            Path context = (Path) e.context();
            Path get = Paths.get(d.toString(), context.toString());

            if (kind == StandardWatchEventKinds.OVERFLOW) {
              onOverflow(get);
            } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
              onCreate(get);
            } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
              onDelete(get);
            } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
              onModify(get);
            }
          }
          taked.reset();
        }
      } catch (IOException | InterruptedException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

  public void postRun() {
  }

  public abstract void onOverflow(Path f);

  public abstract void onCreate(Path f);

  public abstract void onDelete(Path f);

  public abstract void onModify(Path f);

  public Path getDir() {
    return dir;
  }

  public void setDir(Path dir) {
    this.dir = dir;
  }
}
